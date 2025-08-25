package com.webhook.root.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisRetryService {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public void scheduleRetry(String message, long delaySeconds) {
        long executeAt = System.currentTimeMillis() + (delaySeconds * 1000);
        String key = "webhook-retries";
        redisTemplate.opsForZSet().add(key, message, executeAt);
        System.out.println("Scheduled retry in " + delaySeconds + "s");
    }

    public List<String> popReadyMessages() {
        long now = System.currentTimeMillis();
        String key = "webhook-retries";
        
        // Get ready messages
        Set<String> ready = redisTemplate.opsForZSet()
            .rangeByScore(key, 0, now);
        
        if (!ready.isEmpty()) {
            // Remove them so they don't get processed again
            redisTemplate.opsForZSet()
                .removeRangeByScore(key, 0, now);
        }
        
        return new ArrayList<>(ready);
    }
}