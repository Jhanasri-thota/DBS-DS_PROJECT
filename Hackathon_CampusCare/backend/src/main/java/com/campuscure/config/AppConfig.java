package com.campuscure.config;
import org.springframework.context.annotation.*;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.servlet.config.annotation.*;
@Configuration public class AppConfig implements WebMvcConfigurer {
@Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
@Override public void addCorsMappings(CorsRegistry r){r.addMapping("/api/**").allowedOrigins("http://localhost:5173").allowedMethods("*").allowedHeaders("*");}
}