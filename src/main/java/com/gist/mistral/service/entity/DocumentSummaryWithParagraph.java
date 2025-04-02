package com.gist.mistral.service.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DocumentSummaryWithParagraph extends DocumentSummary {
	private List<DocumentSummaryParagraph> paragraphs;
}
