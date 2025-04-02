package com.gist.mistral.service.entity;

import lombok.Data;

@Data
public abstract class DocumentSummary {
	private String shortDescription;
	private DocumentIndex index;
}
