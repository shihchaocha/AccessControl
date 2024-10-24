package org.example.pdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PdpApplication {
    @Bean
    public WebClient webClientPIP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8092")  // 設置基礎 URL
                .build();
    }

    @Bean
    public WebClient webClientPAP() {
        return WebClient.builder()
                .baseUrl("http://localhost:8091")  // 設置基礎 URL
                .build();
    }
    public static void main(String[] args) {
        SpringApplication.run(PdpApplication.class, args);
    }

}
