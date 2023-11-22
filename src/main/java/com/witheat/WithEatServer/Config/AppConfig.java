package com.witheat.WithEatServer.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    String[] accessURL = {"http://localhost:3000", "https://localhost:3000"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(accessURL)   // 허용할 출처
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")   // 허용할 HTTP method
                .allowedHeaders("*")
                .allowCredentials(true)    // 쿠키 인증 요청 허용
                .maxAge(3000);
    }
}