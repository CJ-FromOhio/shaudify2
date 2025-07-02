package com.hezix.shaudifymain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShaudifyMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaudifyMainApplication.class, args);

    }
}
