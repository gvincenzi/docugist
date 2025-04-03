package com.gist.docugist.service.client.request;

import lombok.Data;

@Data
public class MistralAIOCRRequest {
	String model = "mistral-ocr-latest";
	private MistralAIOCRObjectRequest document;
	private Boolean include_image_base64 = Boolean.TRUE;
}
