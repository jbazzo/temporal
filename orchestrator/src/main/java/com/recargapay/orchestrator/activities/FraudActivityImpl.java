package com.recargapay.orchestrator.activities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FraudActivityImpl implements FraudActivity {

    @Override
    public void verify() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://fraud:9999/api/v1/fraud";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class );
        System.out.println(response.getBody());
    }
}
