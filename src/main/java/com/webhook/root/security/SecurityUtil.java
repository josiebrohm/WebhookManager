package com.webhook.root.security;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.webhook.root.model.PublisherAccount;
import com.webhook.root.repository.PublisherAccountRepository;

public class SecurityUtil {

	private static final String HMAC_ALGORITHM = "HmacSHA256";

	/**
	 * Sign provided payload using provided timestamp, secret, and Hmac algorithm
	 * @param payload
	 * @param timestamp
	 * @param secret
	 * @return signed string
	 * @throws Exception
	 */
	public static String signWebhookPayload(String payload, long timestamp, String secret) throws Exception {
		String unsignedString = timestamp + "." + payload;

		Mac sha256Hmac = Mac.getInstance(HMAC_ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), HMAC_ALGORITHM);
		sha256Hmac.init(keySpec);
		
		byte[] hash = sha256Hmac.doFinal(unsignedString.getBytes());
		return Base64.getEncoder().encodeToString(hash);
	}

	/**
	 * Verify that the webhook signature received matches that expected given the payload, timestamp, and secret
	 * @param payload
	 * @param timestamp
	 * @param secret
	 * @param signature
	 * @return true if signature matches expected, false otherwise
	 * @throws Exception
	 */
	public static boolean verifyWebhookSignature(String payload, long timestamp, String secret, String signature) throws Exception {
		String expected = signWebhookPayload(payload, timestamp, secret);
		return expected.equals(signature);
	}

	/**
	 * Get details of currently logged in user from SecurityContextHolder
	 * @param publisherRepository
	 * @return
	 */
    public static PublisherAccount getCurrentPublisher(PublisherAccountRepository publisherRepository) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return publisherRepository.findByUsername(username);
    }
}
