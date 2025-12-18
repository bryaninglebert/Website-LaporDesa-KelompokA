package com.lapordesa.backend.dto;

// Ini adalah 'Data Transfer Object' (DTO)
// Spring akan mengubah JSON dari Vue menjadi objek ini
public class LoginDTO {
    private String username;
    private String password;

    // Getter dan Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}