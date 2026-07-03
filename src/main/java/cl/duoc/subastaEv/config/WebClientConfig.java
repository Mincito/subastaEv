package cl.duoc.subastaEv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient loteWebClient(
            @Value("${lote.service.url}") String loteServiceUrl) {

        return WebClient.builder()
                .baseUrl(loteServiceUrl)
                .build();
    }
}