package org.nahsi.service.poc.controller;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping(value = "/retrieve")
public class RetrievalController {

  @GetMapping
  public ResponseEntity<String> retrieveData() {

    try {

      RestClient restClient = RestClient.create();
      String responseAsString = restClient.get()
          .uri("https://randomuser.me/api/?nat=ch")
          .retrieve()
          .body(String.class);

      JSONObject jsonObject = new JSONObject(responseAsString);

      return ResponseEntity.ok(jsonObject.toString());
    
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.ok("Internal server error: " + e.getMessage());
    }

  }

}
