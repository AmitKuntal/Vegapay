package com.vegapay.demo.dto;

import lombok.Data;

@Data
public class GenericResponse {
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;

    public String message;
}
