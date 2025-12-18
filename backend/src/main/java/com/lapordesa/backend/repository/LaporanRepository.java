package com.lapordesa.backend.repository;

import com.lapordesa.backend.entity.Laporan; // Impor 'Blueprint' Laporan kita
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 1. Menandakan ini adalah 'Penyimpanan'
public interface LaporanRepository extends JpaRepository<Laporan, Long> {
    // 2. JpaRepository<Laporan, Long> artinya:
    // "Ini adalah penyimpanan untuk blueprint 'Laporan',
    // dan Tipe ID (Primary Key) dari 'Laporan' adalah 'Long'"

    // 3. SELESAI!
    // Hanya dengan ini, kita sudah otomatis mendapatkan perintah:
    // - save() -> (Untuk INSERT dan UPDATE)
    // - findAll() -> (Untuk SELECT *)
    // - findById() -> (Untuk SELECT ... WHERE id=?)
    // - deleteById() -> (Untuk DELETE ... WHERE id=?)
    // - dan masih banyak lagi!
}