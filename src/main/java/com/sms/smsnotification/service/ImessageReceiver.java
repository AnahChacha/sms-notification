package com.sms.smsnotification.service;

import com.sms.smsnotification.smsDto.AuthTokenResponse;
import com.sms.smsnotification.smsDto.SmsRequest;

import java.io.IOException;

public interface ImessageReceiver {
    AuthTokenResponse generateToken();

    void sendSms(SmsRequest smsRequest) throws IOException;


}
