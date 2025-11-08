package org.example.gateway.Service;


import org.example.gateway.Feign.UserClient;
import org.example.gateway.Models.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GatewayService {

    private final WebClient webClient = WebClient.create("http://user-api:8080");

    public Mono<ResponseEntity<String>> signup(SiteUser siteUser) {
        return webClient.post()
                .uri("/signup") // direct path to User-api
                .bodyValue(siteUser)
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> ResponseEntity.ok(body))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body("Error: " + e.getMessage())));
    }

}
