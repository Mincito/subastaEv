package cl.duoc.subastaEv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient loteWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8086/api/lotes")
                .build();
    }
}