package com.sms.smsnotification.controller;

import com.sms.smsnotification.service.ImessageReceiver;
import com.sms.smsnotification.smsDto.AuthTokenResponse;
import com.sms.smsnotification.smsDto.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
public class MessageReceiverController {
    private final ImessageReceiver imessageReceiver;

    @PostMapping(value = "/generateToken")
    public ResponseEntity<AuthTokenResponse>generateToken(){
        return ResponseEntity.status(HttpStatus.OK).body(imessageReceiver.generateToken());
    }

    @PostMapping(value = "/smsNotification")
    public void sendSms( @RequestBody SmsRequest smsRequest) throws IOException {
        imessageReceiver.sendSms(smsRequest);

    }
}
