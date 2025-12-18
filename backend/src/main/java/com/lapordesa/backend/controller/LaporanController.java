package com.lapordesa.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lapordesa.backend.dto.LaporanDTO;
import com.lapordesa.backend.dto.UpdateStatusDTO;
import com.lapordesa.backend.entity.Laporan;
import com.lapordesa.backend.service.LaporanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map; // Import baru untuk pesan balasan

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    // 1. MEMBUAT LAPORAN BARU (WARGA)
    @PostMapping("/laporan")
    public ResponseEntity<?> terimaLaporanBaru(
            @RequestParam("laporan") String laporanJson,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            LaporanDTO laporanDTO = objectMapper.readValue(laporanJson, LaporanDTO.class);

            Laporan laporanTersimpan = laporanService.createLaporan(laporanDTO, file);
            return ResponseEntity.ok(laporanTersimpan);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal memproses laporan: " + e.getMessage());
        }
    }

    // 2. MENGAMBIL SEMUA LAPORAN
    @GetMapping("/laporan")
    public ResponseEntity<List<LaporanDTO>> ambilSemuaLaporan() {
        List<LaporanDTO> semuaLaporan = laporanService.getAllLaporan();
        return ResponseEntity.ok(semuaLaporan);
    }

    // 3. MENGAMBIL SATU LAPORAN DETAIL
    @GetMapping("/laporan/{id}")
    public ResponseEntity<LaporanDTO> ambilLaporanBerdasarkanId(@PathVariable Long id) {
        LaporanDTO laporan = laporanService.getLaporanById(id);
        return ResponseEntity.ok(laporan);
    }

    // 4. UPDATE STATUS & TANGGAPAN (ADMIN)
    @PutMapping("/laporan/{id}")
    public ResponseEntity<?> updateLaporan(
            @PathVariable Long id,
            @RequestParam("data") String updateDataJson,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateStatusDTO updateDTO = objectMapper.readValue(updateDataJson, UpdateStatusDTO.class);

            Laporan laporanUpdated = laporanService.updateLaporan(id, updateDTO, file);

            return ResponseEntity.ok(laporanUpdated);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mengupdate laporan: " + e.getMessage());
        }
    }

    // =============================================
    // 5. ENDPOINT BARU: HAPUS LAPORAN (DELETE)
    // =============================================
    @DeleteMapping("/laporan/{id}")
    public ResponseEntity<?> hapusLaporan(@PathVariable Long id) {
        try {
            laporanService.deleteLaporan(id);
            // Kirim balasan JSON yang jelas
            return ResponseEntity.ok(Map.of("message", "Laporan berhasil dihapus"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Gagal menghapus laporan: " + e.getMessage()));
        }
    }
}