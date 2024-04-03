package com.example.WebClientResponseEntityExceptionHandling.Controller;

import com.example.WebClientResponseEntityExceptionHandling.Model.Pet;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class PetController {

    private final WebClient webClient;

    private final static String url = "http://localhost:8080";

    @Value("${app.user}")
    String user;
    @Value("${app.pass}")
    String pass;

    public PetController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "pets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Pet>>> getPets(){
        return webClient.get()
                .uri(url + "/pets")
                .header("Authorization", "Basic " +getBase64(user, pass))
                .retrieve()
                .bodyToMono(Pet[].class)
                .map(pets -> ResponseEntity.ok(Arrays.asList(pets)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "pets/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Pet>>> petsByName(@PathVariable(name = "name")String name){
        String uri = "/pets/" + name;
        return webClient.get()
                .uri(url + uri)
                .header("Authorization", "Basic " + getBase64(user, pass))
                .retrieve()
                .bodyToMono(Pet.class)
                .map(pets -> ResponseEntity.ok(Arrays.asList(pets)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private String getBase64(String user, String password){
        String cad = user + ":" + password;
        return Base64.getEncoder().encodeToString(cad.getBytes());
    }
}
