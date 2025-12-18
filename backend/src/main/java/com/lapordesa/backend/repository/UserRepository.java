package com.lapordesa.backend.repository;

import com.lapordesa.backend.entity.User; // Impor 'Blueprint' User kita
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 1. Menandakan ini adalah 'Penyimpanan'
public interface UserRepository extends JpaRepository<User, Long> {
    // 2. JpaRepository<User, Long> artinya:
    // "Ini adalah penyimpanan untuk blueprint 'User',
    // dan Tipe ID (Primary Key) dari 'User' adalah 'Long'"

    // 3. Tambahkan fungsi pencarian kustom:
    // Hanya dengan menulis baris ini, JPA akan otomatis
    // membuatkan kita kueri SQL "SELECT * FROM users WHERE username = ?"
    Optional<User> findByUsername(String username);
}