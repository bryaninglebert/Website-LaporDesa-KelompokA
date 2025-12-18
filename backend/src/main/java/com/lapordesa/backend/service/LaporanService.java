package com.lapordesa.backend.service;

import com.lapordesa.backend.dto.LaporanDTO;
import com.lapordesa.backend.dto.UpdateStatusDTO;
import com.lapordesa.backend.entity.Laporan;
import com.lapordesa.backend.repository.LaporanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime; // Import baru
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LaporanService {

    @Autowired
    private LaporanRepository laporanRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ... (fungsi createLaporan, getAllLaporan, getLaporanById tidak berubah) ...
    public Laporan createLaporan(LaporanDTO laporanDTO, MultipartFile file) throws IOException {
        String fotoUrl = null;
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = Paths.get(uploadDir, uniqueFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            fotoUrl = uniqueFileName;
        }
        Laporan laporanBaru = new Laporan(
                laporanDTO.getNama(),
                laporanDTO.getTelepon(),
                laporanDTO.getKategori(),
                laporanDTO.getJudul(),
                laporanDTO.getIsi(),
                fotoUrl);
        return laporanRepository.save(laporanBaru);
    }

    public List<LaporanDTO> getAllLaporan() {
        return laporanRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LaporanDTO getLaporanById(Long id) {
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan dengan ID: " + id));
        return convertToDTO(laporan);
    }

    // =============================================
    // UPGRADE FUNGSI UPDATE (TAMBAHKAN TANGGAL UPDATE)
    // =============================================
    public Laporan updateLaporan(Long id, UpdateStatusDTO updateDTO, MultipartFile fileTanggapan) throws IOException {
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan"));

        laporan.setStatus(updateDTO.getStatus());
        laporan.setTanggapanAdmin(updateDTO.getTanggapan());

        // --- TAMBAHAN BARU ---
        laporan.setTanggalUpdate(LocalDateTime.now()); // Set tanggal update ke "sekarang"
        // ---------------------

        if (fileTanggapan != null && !fileTanggapan.isEmpty()) {
            String originalFileName = fileTanggapan.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + "_tanggapan" + fileExtension;
            Path filePath = Paths.get(uploadDir, uniqueFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, fileTanggapan.getBytes());
            laporan.setFotoTanggapanUrl(uniqueFileName);
        }

        return laporanRepository.save(laporan);
    }

    public void deleteLaporan(Long id) throws IOException {
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan"));
        if (laporan.getFotoUrl() != null) {
            Path filePath = Paths.get(uploadDir, laporan.getFotoUrl());
            Files.deleteIfExists(filePath);
        }
        if (laporan.getFotoTanggapanUrl() != null) {
            Path filePathTanggapan = Paths.get(uploadDir, laporan.getFotoTanggapanUrl());
            Files.deleteIfExists(filePathTanggapan);
        }
        laporanRepository.delete(laporan);
    }

    // =============================================
    // UPGRADE FUNGSI convertToDTO (KIRIM TANGGAL KE FRONTEND)
    // =============================================
    private LaporanDTO convertToDTO(Laporan laporanEntity) {
        LaporanDTO dto = new LaporanDTO();
        dto.setId(laporanEntity.getId());
        dto.setNama(laporanEntity.getNama());
        dto.setTelepon(laporanEntity.getTelepon());
        dto.setKategori(laporanEntity.getKategori());
        dto.setJudul(laporanEntity.getJudul());
        dto.setIsi(laporanEntity.getIsi());
        dto.setStatus(laporanEntity.getStatus());
        dto.setFotoUrl(laporanEntity.getFotoUrl());
        dto.setTanggapanAdmin(laporanEntity.getTanggapanAdmin());
        dto.setFotoTanggapanUrl(laporanEntity.getFotoTanggapanUrl());

        // --- TAMBAHAN BARU ---
        dto.setTanggalLaporan(laporanEntity.getTanggalLaporan());
        dto.setTanggalUpdate(laporanEntity.getTanggalUpdate()); // Kirim tanggal update

        return dto;
    }
}