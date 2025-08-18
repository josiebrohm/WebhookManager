package com.webhook.root.controller;

import com.webhook.root.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/run")
    public String runBackgroundTask() {
        asyncService.executeAsyncTask();
        return "Background task is running...";
    }
}
