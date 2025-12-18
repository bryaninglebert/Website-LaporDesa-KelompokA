package com.lapordesa.backend.dto;

// Ini adalah 'formulir' yang dikirim admin
// saat mengklik "Simpan Perubahan"
public class UpdateStatusDTO {

    private String status;
    private String tanggapan; // Teks tanggapan dari admin

    // Getter dan Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public void setTanggapan(String tanggapan) {
        this.tanggapan = tanggapan;
    }
}