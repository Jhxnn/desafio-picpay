package com.picpay_desafio.desafio_picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.picpay_desafio.models")
@SpringBootApplication
public class PicpayDesafioApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicpayDesafioApplication.class, args);
    }
}
