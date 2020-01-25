package com.southcn.nfapp.ncov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NfplusNcovApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfplusNcovApplication.class, args);
    }

}
