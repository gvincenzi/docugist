package com.gist.mistral.service.client.response;

import java.util.List;

import com.gist.mistral.service.client.request.MistralAIOCRPageResponse;

import lombok.Data;

@Data
public class MistralAIOCRResponse {
	List<MistralAIOCRPageResponse> pages; 
}
