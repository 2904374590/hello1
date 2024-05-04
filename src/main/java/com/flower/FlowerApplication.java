package com.flower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
@MapperScan("com.flower.dao")
public class FlowerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowerApplication.class, args);
    }

}
