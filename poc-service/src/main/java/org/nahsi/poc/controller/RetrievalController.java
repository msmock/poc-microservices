package org.nahsi.poc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/retrieve")
public class RetrievalController {

  @GetMapping
  public ResponseEntity<String> retrieveData() {

    // TODO make json call via gateway

    // get a random user
    WebClient webClient = WebClient.create("https://randomuser.me/api/?nat=ch");
    Mono<String> result = webClient.get().retrieve().bodyToMono(String.class);
    String responseString = result.block();

    return ResponseEntity.ok(responseString);

  }

}
