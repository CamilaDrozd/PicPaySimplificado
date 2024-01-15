package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuhtorizationService {

    @Autowired
    private RestTemplate restTemplate;


    public boolean authorizedTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationresponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class );

        if(authorizationresponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationresponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }
}
