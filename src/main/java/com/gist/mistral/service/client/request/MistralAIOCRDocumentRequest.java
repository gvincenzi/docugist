package com.gist.mistral.service.client.request;

import lombok.Data;

@Data
public class MistralAIOCRDocumentRequest implements MistralAIOCRObjectRequest {
	String type = "document_url";
	String document_url;
}
