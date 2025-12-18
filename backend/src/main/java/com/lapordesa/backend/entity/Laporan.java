package com.lapordesa.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime; // <-- Pastikan ini ada

@Entity
@Table(name = "laporan")
public class Laporan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String telepon;

    @Column(nullable = false)
    private String kategori;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT")
    private String isi;

    @Column(nullable = false)
    private String status;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(columnDefinition = "TEXT")
    private String tanggapanAdmin;

    @Column(name = "foto_tanggapan_url")
    private String fotoTanggapanUrl;

    // --- PENGATURAN TANGGAL ---

    @Column(name = "tanggal_laporan")
    private LocalDateTime tanggalLaporan; // Tanggal dibuat

    @Column(name = "tanggal_update")
    private LocalDateTime tanggalUpdate; // Tanggal terakhir diubah

    // --- Constructor ---

    public Laporan() {
    }

    public Laporan(String nama, String telepon, String kategori, String judul, String isi, String fotoUrl) {
        this.nama = nama;
        this.telepon = telepon;
        this.kategori = kategori;
        this.judul = judul;
        this.isi = isi;
        this.fotoUrl = fotoUrl;

        // --- PERBAIKAN ADA DI SINI (TITIK KOMA) ---
        this.tanggalLaporan = LocalDateTime.now(); // Set tanggal dibuat
        this.tanggalUpdate = LocalDateTime.now(); // Set tanggal update (awalnya sama)
        this.status = "Terkirim";
    }

    // --- Getter dan Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getTanggapanAdmin() {
        return tanggapanAdmin;
    }

    public void setTanggapanAdmin(String tanggapanAdmin) {
        this.tanggapanAdmin = tanggapanAdmin;
    }

    public String getFotoTanggapanUrl() {
        return fotoTanggapanUrl;
    }

    public void setFotoTanggapanUrl(String fotoTanggapanUrl) {
        this.fotoTanggapanUrl = fotoTanggapanUrl;
    }

    public LocalDateTime getTanggalLaporan() {
        return tanggalLaporan;
    }

    public void setTanggalLaporan(LocalDateTime tanggalLaporan) {
        this.tanggalLaporan = tanggalLaporan;
    }

    public LocalDateTime getTanggalUpdate() {
        return tanggalUpdate;
    }

    public void setTanggalUpdate(LocalDateTime tanggalUpdate) {
        this.tanggalUpdate = tanggalUpdate;
    }
}