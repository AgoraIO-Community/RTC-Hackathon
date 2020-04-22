package com.asdf.funny.images;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.asdf.funny.images.dao")
public class ImagesApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ImagesApplication.class, args);
    }

}
