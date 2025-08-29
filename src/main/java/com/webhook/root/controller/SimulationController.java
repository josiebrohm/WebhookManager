package com.webhook.root.controller;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulate")
public class SimulationController {

    private final Random random = new Random(); 

    @PostMapping("/200")
    @ResponseStatus(HttpStatus.OK)
    public String simlate200() {
        return "Simulated 200 OK";
    }
    
    @PostMapping("/400")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String simulate400() {
        return "Simulated 400 Bad Request";
    }

    @PostMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String simulate500() {
        return "Simulated 500 Internal Server Error";
    }

    @PostMapping("/chaos")
    public ResponseEntity<String> simulateRandom() {
        HttpStatus[] statuses = HttpStatus.values();
        HttpStatus randomStatus = statuses[random.nextInt(statuses.length)];

         return ResponseEntity.status(randomStatus)
            .body("Response status: " + randomStatus.value() + " " + randomStatus.getReasonPhrase());
    }
}