package com.gist.docugist.service.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DocumentQuiz extends DocumentSummary{
	List<DocumentQuizQuestion> questions;
}
