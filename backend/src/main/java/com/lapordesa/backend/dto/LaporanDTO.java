package com.lapordesa.backend.dto;

import java.time.LocalDateTime;

public class LaporanDTO {
    private Long id;
    private String nama;
    private String telepon;
    private String kategori;
    private String judul;
    private String isi;
    private String status;
    private String fotoUrl;
    private String tanggapanAdmin;
    private String fotoTanggapanUrl;

    // --- TAMBAHAN BARU (TANGGAL) ---
    private LocalDateTime tanggalLaporan;
    private LocalDateTime tanggalUpdate;

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

    // --- GETTER/SETTER BARU UNTUK TANGGAL ---
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