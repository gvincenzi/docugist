package com.gist.mistral.service.client.request;

import lombok.Data;

@Data
public class MistralAIOCRImageRequest implements MistralAIOCRObjectRequest{
	String type = "image_url";
	String image_url;
}
