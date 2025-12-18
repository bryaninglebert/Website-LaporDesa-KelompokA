package com.lapordesa.backend.service;

import com.lapordesa.backend.entity.User;
import com.lapordesa.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- 1. IMPOR ALAT ENKRIPSI
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // =============================================
    // TAMBAHAN BARU: SUNTIK 'GEMBOK' (BCRYPT)
    // =============================================
    @Autowired
    private PasswordEncoder passwordEncoder; // <-- 2. SUNTIK ALAT ENKRIPSI

    /**
     * Kita ubah fungsi ini untuk mengecek password yang sudah dienkripsi
     */
    public String verifyLogin(String username, String password) {

        // 4. Cari user di database
        Optional<User> userOptional = userRepository.findByUsername(username);

        // 5. Cek jika user-nya TIDAK ADA
        if (userOptional.isEmpty()) {
            return "Username atau password salah."; // Pesan aman
        }

        // 6. Jika user-nya ADA, kita ambil datanya
        User user = userOptional.get();

        // =============================================
        // PERUBAHAN UTAMA: LOGIKA PENGECEKAN PASSWORD
        // =============================================

        // 7. 'password' = password teks biasa dari frontend (misal: "admin123")
        // 'user.getPassword()' = password terenkripsi dari database (misal:
        // "$2a$10$...")
        //
        // 'passwordEncoder.matches' akan mencocokkan keduanya.
        if (passwordEncoder.matches(password, user.getPassword())) {

            // 8. SUKSES! Password cocok!
            String nama = user.getNamaLengkap() != null ? user.getNamaLengkap() : user.getUsername();
            return "Selamat datang, " + nama + "!";

        } else {
            // 9. Password salah
            return "Username atau password salah."; // Pesan aman
        }
    }

    // =============================================
    // FUNGSI BARU: GANTI PASSWORD
    // =============================================
    public String changePassword(String username, String oldPassword, String newPassword) {
        // 1. Cari user
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "User tidak ditemukan.";
        }
        User user = userOptional.get();

        // 2. Cek apakah password lama COCOK dengan database
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "Password lama salah.";
        }

        // 3. Enkripsi password baru
        String encryptedNewPassword = passwordEncoder.encode(newPassword);

        // 4. Simpan password baru ke database
        user.setPassword(encryptedNewPassword);
        userRepository.save(user);

        return "Berhasil mengganti password!";
    }
}