package com.campuscare.config;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class AppConfig implements WebMvcConfigurer {
 @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
 @Override public void addCorsMappings(CorsRegistry registry){
  registry.addMapping("/api/**").allowedOrigins("http://localhost:5173","http://127.0.0.1:5173")
   .allowedMethods("GET","POST","PUT","DELETE","OPTIONS").allowedHeaders("*");
 }
}
