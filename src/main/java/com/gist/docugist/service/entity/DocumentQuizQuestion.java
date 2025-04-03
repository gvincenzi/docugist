package com.gist.docugist.service.entity;

import java.util.List;

import lombok.Data;

@Data
public class DocumentQuizQuestion {
	private String question;
	private List<DocumentQuizResponse> responses;
}
