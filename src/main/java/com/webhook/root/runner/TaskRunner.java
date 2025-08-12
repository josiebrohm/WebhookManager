package com.webhook.root.runner;

import com.webhook.root.model.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskRunner {

    public boolean runTask(TaskEntity task) {
        // task logic here
        // send webhook
        try {
            // send webhook
            return true;
        } catch (Exception e) { // TODO: be more specific about exceptions
            System.err.println(e.getMessage());
            return false;
        }
    }
}
