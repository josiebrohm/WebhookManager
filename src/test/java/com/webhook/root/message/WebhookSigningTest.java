package com.webhook.root.message;

import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webhook.root.model.Endpoint;
import com.webhook.root.model.WebhookMessage;
import com.webhook.root.security.SecurityUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(WebhookMessageSender.class)
public class WebhookSigningTest {
	
	@Autowired
	private RestTemplate webhookRestTemplate;

	@Autowired
	private WebhookMessageSender sender;

	@Autowired
	private MockRestServiceServer server;

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String secret = "shh-secret-key";
	private final String url = "https://webhook.site/6e8e440a-df1d-4a08-8c5d-47746da2554d";

	@BeforeEach
	void setup() {
		server.reset();
	}

	@Test
	void testWebhookSigningHeadersPresentAndValid() throws Exception {
		Map<String, Object> payload = Map.of("foo", "bar");

		server.expect(requestTo(url))
			.andExpect(method(HttpMethod.POST))
			.andExpect(header("X-Signature", Matchers.notNullValue()))
			.andExpect(header("X-Timestamp", Matchers.notNullValue()))
			.andRespond(withSuccess("ok", MediaType.APPLICATION_JSON));

		WebhookMessage msg = new WebhookMessage();
		msg.setPayload(payload);
		msg.setEndpoint(new Endpoint(url, "shh-secret-key", true));

		sender.sendWebhookMessage(msg);
		server.verify();
	}

	@Test
	void testWebhookSignatureActuallyValid() throws Exception {
		Map<String, Object> payload = Map.of("foo", "bar");
		String body = objectMapper.writeValueAsString(payload);

		server.expect(requestTo(url))
			.andExpect(method(HttpMethod.POST))
			.andExpect(request -> {
				String signature = request.getHeaders().getFirst("X-Signature");
				String timestamp = request.getHeaders().getFirst("X-Timestamp");

				assertThat(signature).isNotNull();
				assertThat(timestamp).isNotNull();

				// recalculate expected signature
				String expected;
				try {
					expected = SecurityUtil.signWebhookPayload(body, Long.parseLong(timestamp), secret);
					assertThat(signature).isEqualTo(expected);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			})
			.andRespond(withSuccess("ok", MediaType.APPLICATION_JSON));
	}
}
