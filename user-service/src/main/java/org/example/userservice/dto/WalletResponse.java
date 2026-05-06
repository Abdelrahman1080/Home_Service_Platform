package org.example.userservice.dto;


public class WalletResponse {

    private boolean success;
    private String message;

    public WalletResponse() {}

    public WalletResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

