package com.recargapay.fraud.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FraudController {

    @GetMapping("/api/v1/fraud")
    public String verify() {
        System.out.println("en fraude");
        return "en fraude";
    }
}
