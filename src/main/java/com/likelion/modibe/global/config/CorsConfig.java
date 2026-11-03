package com.likelion.modibe.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // 허용할 프론트엔드 주소(여기에 추가해주세요!! 일단 임의로 프론트 주소 넣어놨어여)
        config.setAllowedOrigins(List.of(
                "http://localhost:5173"
        ));

        // 허용할 HTTP 메서드
        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        // 허용할 요청 헤더
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        // 쿠키 기반 인증을 사용하지 않으므로 비활성화
        config.setAllowCredentials(false);

        // Preflight 요청 결과 캐싱 시간
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}