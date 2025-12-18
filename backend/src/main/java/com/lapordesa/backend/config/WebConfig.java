package com.lapordesa.backend.config;

import org.springframework.beans.factory.annotation.Value; // Import baru
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry; // Import baru
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Mengambil lokasi folder upload dari 'application.properties'
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Konfigurasi CORS (Cross-Origin Resource Sharing)
     * Ini untuk mengizinkan Frontend (Vue) berbicara dengan Backend (Spring)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Berlaku untuk SEMUA endpoint di bawah /api/
                .allowedOrigins("http://localhost:5173") // Izinkan panggilan dari frontend Anda
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Izinkan semua metode ini
                .allowedHeaders("*") // Izinkan semua header
                .allowCredentials(false);
    }

    // =============================================
    // TAMBAHAN BARU: Membuat "Server File"
    // =============================================
    /**
     * Konfigurasi Resource Handler
     * Ini akan memetakan URL ke folder di server Anda
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. URL Path: Alamat yang akan diakses oleh frontend
        // (contoh: http://localhost:8080/api/uploads/namafile.jpg)
        String resourcePath = "/api/uploads/**";

        // 2. File Location: Lokasi folder fisik di server
        // (Kita ambil dari 'application.properties', yaitu 'file:./uploads/')
        String resourceLocation = "file:" + uploadDir + "/";

        System.out.println("Memetakan " + resourcePath + " ke " + resourceLocation);

        registry.addResourceHandler(resourcePath)
                .addResourceLocations(resourceLocation);
    }
}