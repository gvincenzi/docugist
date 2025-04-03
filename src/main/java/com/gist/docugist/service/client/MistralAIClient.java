package com.gist.docugist.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gist.docugist.configuration.MistralAIFeignConfiguration;
import com.gist.docugist.service.client.request.MistralAIOCRRequest;
import com.gist.docugist.service.client.response.MistralAIOCRResponse;

@FeignClient(name = "${feign.mistralai.name}", url = "${feign.mistralai.url}", configuration = MistralAIFeignConfiguration.class)
public interface MistralAIClient {
	@PostMapping(value = "/v1/ocr")
	MistralAIOCRResponse ocr(@RequestBody MistralAIOCRRequest request);
}
