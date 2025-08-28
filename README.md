# Webhook Manager

## Example usage (for testing)
1. Ensure docker is running
2. Start `WebhookManagerApplication.java`
3. Make a POST request to `/endpoints` with the following JSON body (change the webhook.site url to your own):
	```json
	{
		"url": "https://webhook.site/d46d684f-4612-41b2-aae4-d56dd5329409",
		"secret": "secret",
		"enabled": true
	}
4. Make a POST request to `/accounts` with the following JSON body:
	```json
	{
		"name": "Publisher Name",
		"api_key": "API_KEY",
		"rate_limit": 1
	}
5. Obtain the Endpoint ID and Publisher Account ID needed to create a Webhook Message by making GET requests to the respective endpoints. 
	NOTE: these GET requests return ALL stored endpoints and accounts, so make sure you choose the correct IDs.<br/>
	a. `GET {baseurl}/endpoints` (attribute is labeled `id`) <br/>
	b. `GET {baseurl}/accounts` (attribute is labeled `id`)
6. Finally, make a POST request to `/webhooks` with the following JSON body (replace the endpoint and publisher account IDs with your own):
	```json
	{
		"endpoint_id": "6e12091b-a260-415d-8f29-316168c13709",
		"publisher_account_id": "dbe919d8-b395-45f7-8b82-076d8556618a",
		"headers": {
			"header1": "v1",
			"header2": "v2"
		},
		"payload": {
			"prop1": "val1",
			"prop2": "val2"
		},
		"event_type": "test.send"
	}