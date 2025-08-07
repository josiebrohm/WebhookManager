package com.webhook.root.model;

public class Webhook {
    private String method;
    private String url;
    private String requestBody;

    public Webhook(String method, String url, String requestBody) {
        this.method = method;
        this.url = url;
        this.requestBody = requestBody;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
