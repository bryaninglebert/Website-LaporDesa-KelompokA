package com.lapordesa.backend.controller;

import com.lapordesa.backend.dto.ChangePasswordDTO; // Import DTO baru
import com.lapordesa.backend.dto.LoginDTO;
import com.lapordesa.backend.dto.LoginResponseDTO;
import com.lapordesa.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String serviceResponse = authService.verifyLogin(loginDTO.getUsername(), loginDTO.getPassword());
        if (serviceResponse.startsWith("Selamat datang")) {
            return ResponseEntity.ok(new LoginResponseDTO(true, serviceResponse));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO(false, serviceResponse));
        }
    }

    // =============================================
    // ENDPOINT BARU: GANTI PASSWORD
    // =============================================
    @PostMapping("/change-password")
    public ResponseEntity<LoginResponseDTO> changePassword(@RequestBody ChangePasswordDTO request) {
        String result = authService.changePassword(
                request.getUsername(),
                request.getOldPassword(),
                request.getNewPassword());

        if (result.equals("Berhasil mengganti password!")) {
            return ResponseEntity.ok(new LoginResponseDTO(true, result));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(false, result));
        }
    }
}