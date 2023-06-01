package com.sms.smsnotification.smsDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTokenResponse{

	@JsonProperty("lifetimeInSeconds")
	private Integer lifetimeInSeconds;

	@JsonProperty("token")
	private String token;
}