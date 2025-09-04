# Webhook Manager

## Example usage (for testing)
1. Ensure docker is running (with kafka and zookeeper)
2. Start `WebhookManagerApplication.java`

### Testing authentication
1. Make a GET request to `/test/all` (unprotected endpoint). You should get the following response: 
	`Public Content.`
2. Make a GET request to `/test/user` (protected endpoint). You should get a `401 Unauthorized` error.

### Creating a Publisher account
3. Make a POST request to `/auth/register` with the following JSON body:
	```json
	{
		"name": "Publisher Name",
		"username": "publisher",
		"password": "password"
	}

### Logging in as a publisher
4. Make a POST request to `/auth/login` with the following JSON body:
	```json
	{
		"username": "publisher",
		"password": "password"
	}
5. Copy the token you receive as a response e.g.
	`eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0cHVibGlzaGVyYXNkYXNAZ21haWwuY29tIiwiaWF0IjoxNzU2OTg3NTkzLCJleHAiOjE3NTY5OTExOTN9.Ej7GCz_iUbjFtuhdGdqNEiAVqFprSs7fLyBPkWqgQNikfISA3uxDB8H1LbOdrj1eH0EP7N8-L_KKUqI7u6ofbw`

### Test protected endpoint
6. Make a GET request to `/test/user` with your token included as a Header with the name "Authorization". Make sure to prefix your token with "Bearer" e.g. <br/>
	`Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0cHVibGlzaGVyYXNkYXNAZ21haWwuY29tIiwiaWF0IjoxNzU2OTg3NTkzLCJleHAiOjE3NTY5OTExOTN9.Ej7GCz_iUbjFtuhdGdqNEiAVqFprSs7fLyBPkWqgQNikfISA3uxDB8H1LbOdrj1eH0EP7N8-L_KKUqI7u6ofbw`


## Creating an endpoint

Make a POST request to `/endpoints` with the following JSON body (change the webhook.site url to your own)
```json
	{
		"url": "https://webhook.site/d46d684f-4612-41b2-aae4-d56dd5329409",
		"secret": "secret",
		"enabled": true
	}
```

## Creating (and sending) a webhook message
1. Obtain the Endpoint ID and Publisher Account ID needed to create a Webhook Message by making GET requests to the respective endpoints. 
	NOTE: these GET requests return ALL stored endpoints and accounts, so make sure you choose the correct IDs.<br/>
	a. `GET {baseurl}/endpoints` (attribute is labeled `id`) <br/>
	b. `GET {baseurl}/accounts` (attribute is labeled `id`)
2. Make a POST request to `/webhooks` with the following JSON body (replace the endpoint and publisher account IDs with your own)
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
