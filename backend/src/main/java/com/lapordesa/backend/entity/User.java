package com.lapordesa.backend.entity;

// Pastikan Anda mengimpor dari 'jakarta.persistence' (bukan 'javax')
// karena kita menggunakan Spring Boot 3
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 1. Memberitahu JPA ini adalah 'blueprint' tabel
@Table(name = "users") // 2. Nama tabel di MySQL nanti akan menjadi 'users'
public class User {

    @Id // 3. Menandakan ini adalah Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Kolom ini akan auto-increment (1, 2, 3, ...)
    private Long id;

    @Column(nullable = false, unique = true) // 5. Kolom 'username', tidak boleh kosong (null) dan tidak boleh sama
    private String username;

    @Column(nullable = false) // 6. Kolom 'password', tidak boleh kosong
    private String password;

    @Column(name = "nama_lengkap") // 7. Kolom 'nama_lengkap'
    private String namaLengkap;

    // --- Getter dan Setter ---
    // JPA membutuhkan constructor kosong (dibuat otomatis)
    // dan getter/setter ini untuk mengakses data.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}