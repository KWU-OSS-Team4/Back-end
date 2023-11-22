package com.witheat.WithEatServer.Config;

import com.witheat.WithEatServer.Config.JWT.JwtAuthenticationFilter;
import com.witheat.WithEatServer.Config.JWT.JwtTokenProvideImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvideImpl jwtTokenProvideImpl;

    private final String[] possibleAccess = {"/api/auth/signup", "/api/auth/login"};   // final 키워드 추가했음

    public SecurityConfig(JwtTokenProvideImpl jwtTokenProvideImpl, RedisTemplate redisTemplate) {
        this.jwtTokenProvideImpl = jwtTokenProvideImpl;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers((header)-> header.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement)->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, possibleAccess).permitAll()
                                .requestMatchers(HttpMethod.GET, possibleAccess).permitAll()
                                .requestMatchers(HttpMethod.DELETE, possibleAccess).permitAll()
                                .requestMatchers(HttpMethod.PATCH, possibleAccess).permitAll()
                                .anyRequest().authenticated()
                );
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvideImpl),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
