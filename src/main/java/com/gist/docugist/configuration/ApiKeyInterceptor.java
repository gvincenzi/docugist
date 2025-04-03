package com.gist.docugist.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ApiKeyInterceptor implements RequestInterceptor {
    private final String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void apply(RequestTemplate template) {
    	template.header("Content-Type", "application/json");
        template.header("Authorization", "Bearer " + apiKey);
    }
}
