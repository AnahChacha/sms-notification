package com.sms.smsnotification.service;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import com.africastalking.sms.SendMessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.smsnotification.smsDto.AuthTokenResponse;
import com.sms.smsnotification.smsDto.SmsRequest;
import com.sms.smsnotification.smsDto.SmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MessageReceiver implements ImessageReceiver{
    private final RestTemplate restTemplate=new RestTemplate();
    private final ObjectMapper mapper=new ObjectMapper();

    @Value("${africastalking.username}")
    private String username;

    @Value("${africastalking.apiKey}")
    private String apiKey;

    @Override
    public AuthTokenResponse generateToken() {
            HttpHeaders headers=new HttpHeaders();
            headers.setBasicAuth(username,apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            String url="https://api.africastalking.com/auth-token/generate";

            UriComponents builder= UriComponentsBuilder.fromHttpUrl(url).build();
            HttpEntity authEntity=new HttpEntity<>(headers);
            ResponseEntity<JsonNode>response=restTemplate.exchange(builder.toString(),HttpMethod.POST,authEntity, JsonNode.class);

            log.info("auth token response {}",response.getBody());

            int statusCodeValue=response.getStatusCodeValue();
            log.info("statusCode {}",statusCodeValue);

            JsonNode responseBody=response.getBody();
        AuthTokenResponse authTokenResponse= null;
        try {
            authTokenResponse = mapper.treeToValue(responseBody, AuthTokenResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return authTokenResponse;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) throws IOException {
        AfricasTalking.initialize(username,apiKey);

        SmsService sms =AfricasTalking.getService(AfricasTalking.SERVICE_SMS);


        String[] recipients=new String[]{"+254708110517","+254789076543"};
        String message= smsRequest.getMessage();

        String from="3600";

        List<Recipient> response=sms.send(message,from,recipients,true);

        for (Recipient recipient:response){
//            System.out.println(recipient.number);
//            System.out.println(" : ");
//            System.out.println(recipient.status);

            log.info("========= {}",recipient.number);
            log.info("====== {}",recipient.status);
            log.info("message id {}",recipient.messageId);
        }

    }
}

