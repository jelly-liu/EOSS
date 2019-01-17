package com.jelly.eoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class EossApplication {
    public static void main(String[] args) {
        SpringApplication.run(EossApplication.class, args);
    }
}
