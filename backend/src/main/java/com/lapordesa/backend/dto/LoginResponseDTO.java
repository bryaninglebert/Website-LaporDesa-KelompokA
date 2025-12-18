package com.lapordesa.backend.dto;

// Ini adalah DTO untuk mengirim balasan ke Vue
public class LoginResponseDTO {
    private boolean success;
    private String message;

    // Constructor untuk membuat objek balasan dengan cepat
    public LoginResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter dan Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}