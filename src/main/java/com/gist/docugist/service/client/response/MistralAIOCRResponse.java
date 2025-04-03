package com.gist.docugist.service.client.response;

import java.util.List;

import com.gist.docugist.service.client.request.MistralAIOCRPageResponse;

import lombok.Data;

@Data
public class MistralAIOCRResponse {
	List<MistralAIOCRPageResponse> pages; 
}
