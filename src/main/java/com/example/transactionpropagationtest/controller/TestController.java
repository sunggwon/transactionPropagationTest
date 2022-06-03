package com.example.transactionpropagationtest.controller;

import com.example.transactionpropagationtest.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final BuyerService service;

    @ResponseBody
    @PostMapping("/test/required")
    public ResponseEntity<String> buyTicketRequired(String name, long count){
        service.buyTicketRequired(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/requiredNew")
    public ResponseEntity<String> buyTicketRequiredNew(String name, long count){
        service.buyTicketRequiredNew(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/supportsInTransaction")
    public ResponseEntity<String> buyTicketSupports(String name, long count){
        service.buyTicketSupports(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/supportsNotTransaction")
    public ResponseEntity<String> buyTicketSupportsNoTransaction(String name, long count){
        service.buyTicketNotSupportedNoTransaction(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/notSupportedTransaction")
    public ResponseEntity<String> buyTicketNotSupported(String name, long count){
        service.buyTicketNotSupported(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/notSupportedNoTransaction")
    public ResponseEntity<String> buyTicketNotSupportedNoTransaction(String name, long count){
        service.buyTicketNotSupportedNoTransaction(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/mandatoryInTransaction")
    public ResponseEntity<String> buyTicketMandatoryInTransaction(String name, long count){
        service.buyTicketMandatoryInTransaction(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/mandatoryNoTransaction")
    public ResponseEntity<String> buyTicketMandatoryNoTransaction(String name, long count){
        service.buyTicketMandatoryNoTransaction(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/test/never")
    public ResponseEntity<String> buyTicketNever(String name, long count){
        service.buyTicketNever(name, count);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
