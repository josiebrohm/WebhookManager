package com.webhook.root.model;

//@Entity
public class Webhook {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String httpMethod; // might be redundant
    private String targetUrl;
    private String payload;

    public Webhook(String httpMethod, String targetUrl, String payload) {
        this.httpMethod = httpMethod;
        this.targetUrl = targetUrl;
        this.payload = payload;
    }

    public Webhook() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "id='" + id + '\'' +
                "httpMethod='" + httpMethod + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }

}
