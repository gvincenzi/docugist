package com.gist.docugist.service.client.request;

import lombok.Data;

@Data
public class MistralAIOCRDocumentRequest implements MistralAIOCRObjectRequest {
	String type = "document_url";
	String document_url;
}
