package com.platform.auth.dto;

public class SendSmsRequest {
    
    private String phone;
    private String type;

    // Standard getter and setter methods
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}