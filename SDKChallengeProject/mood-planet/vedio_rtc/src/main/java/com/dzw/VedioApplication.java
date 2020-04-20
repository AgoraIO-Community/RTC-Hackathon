package com.dzw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.dzw.*"})
@MapperScan("com.dzw.dao.*")
public class VedioApplication {

    public static void main(String[] args) {
        SpringApplication.run(VedioApplication.class, args);
    }

}
