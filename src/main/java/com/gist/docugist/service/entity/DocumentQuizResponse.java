package com.gist.docugist.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DocumentQuizResponse {
	String text;
	
	@JsonProperty(required = false)
	Boolean isRightResponse = Boolean.FALSE;
}
