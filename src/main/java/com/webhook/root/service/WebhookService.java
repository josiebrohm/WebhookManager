package com.webhook.root.service;

import com.webhook.root.model.Webhook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebhookService {
    List<Webhook> webhooks = new ArrayList<>();

    public void receiveWebhook(Webhook webhook) {
        webhooks.add(webhook);
    }
}
