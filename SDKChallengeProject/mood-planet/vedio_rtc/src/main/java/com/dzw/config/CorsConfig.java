package com.dzw.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //withCredentials的情况下，后端要设置Access-Control-Allow-Origin为你的源地址，例如http://localhost:8080，
    // 不能是*，而且还要设置header(‘Access-Control-Allow-Credentials: true’);
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080")
//                .allowCredentials(true)
//                .maxAge(3600)
//                .allowedHeaders("Accept", "Content-Type", "Origin", "Authorization", "X-Auth-Token")
//                .exposedHeaders("X-Auth-Token", "Authorization")
//                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
//    }
}
