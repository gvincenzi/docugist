package com.gist.docugist.service.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DocumentSummaryWithoutParagraph extends DocumentSummary {
	private String summary;
}
