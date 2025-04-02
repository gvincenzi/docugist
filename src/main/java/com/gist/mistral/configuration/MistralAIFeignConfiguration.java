package com.gist.mistral.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MistralAIFeignConfiguration {
	@Value("${spring.ai.mistralai.api-key}")
	private String apiKey;

    @Bean
    ApiKeyInterceptor interceptor() {
        return new ApiKeyInterceptor(apiKey);
    }
}
