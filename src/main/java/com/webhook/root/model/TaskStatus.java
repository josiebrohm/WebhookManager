package com.webhook.root.model;

public enum TaskStatus {

    PENDING("PENDING"),
    QUEUED("QUEUED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
