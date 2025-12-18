package com.lapordesa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Halaman publik (Izin untuk Siapapun)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/laporan").permitAll()
                        .requestMatchers("/api/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/laporan/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/laporan/**").permitAll()

                        // =============================================
                        // TAMBAHAN BARU: IZINKAN ADMIN MENGHAPUS (DELETE)
                        // =============================================
                        .requestMatchers(HttpMethod.DELETE, "/api/laporan/**").permitAll()

                        // Halaman lain harus login
                        .anyRequest().authenticated());

        return http.build();
    }
}