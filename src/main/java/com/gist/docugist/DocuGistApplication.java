package com.gist.docugist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DocuGistApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocuGistApplication.class, args);
    }
}
