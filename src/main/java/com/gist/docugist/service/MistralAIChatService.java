package com.gist.docugist.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.gist.docugist.service.entity.DocumentQuiz;
import com.gist.docugist.service.entity.DocumentSummary;
import com.gist.docugist.service.entity.DocumentSummaryWithParagraph;
import com.gist.docugist.service.entity.DocumentSummaryWithoutParagraph;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@Profile({ "gist" })
public class MistralAIChatService {
	@Autowired
	MistralAiChatModel chatModel;
	
	@Value("classpath:/prompts/summarize_paragraph.st")
	private Resource summarizeParagraph;
	
	@Value("classpath:/prompts/summarize.st")
	private Resource summarize;
	
	@Value("classpath:/prompts/quiz.st")
	private Resource quiz;
	
	public DocumentSummary summary(Resource resource, String lang, Boolean withParagraph) throws InterruptedException, MalformedURLException {
		log.info(String.format("%s -> %s", MistralAIChatService.class.getSimpleName(), "summary"));
		DocumentSummary documentSummary = null;
		if(withParagraph) {
			BeanOutputConverter<DocumentSummaryWithParagraph> beanOutputConverter = new BeanOutputConverter<>(DocumentSummaryWithParagraph.class);
			String format = beanOutputConverter.getFormat();
			
			SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(this.summarizeParagraph);
			Message systemMessage = systemPromptTemplate.createMessage(Map.of("format", format, "language", lang));
			UserMessage userMessage = new UserMessage(resource);
			Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
			
			log.info(String.format("Calling MistralAI"));
			ChatResponse chatResponse = this.chatModel.call(prompt);
			documentSummary = beanOutputConverter.convert(chatResponse.getResult().getOutput().getText());
			log.info(String.format("MistralAI FinishReason : %s", chatResponse.getResult().getMetadata().getFinishReason()));
		} else {
			BeanOutputConverter<DocumentSummaryWithoutParagraph> beanOutputConverter = new BeanOutputConverter<>(DocumentSummaryWithoutParagraph.class);
			String format = beanOutputConverter.getFormat();
			
			SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(this.summarize);
			Message systemMessage = systemPromptTemplate.createMessage(Map.of("format", format, "language", lang));
			UserMessage userMessage = new UserMessage(resource);
			Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
			
			log.info(String.format("Calling MistralAI"));
			ChatResponse chatResponse = this.chatModel.call(prompt);
			documentSummary = beanOutputConverter.convert(chatResponse.getResult().getOutput().getText());
			log.info(String.format("MistralAI FinishReason : %s", chatResponse.getResult().getMetadata().getFinishReason()));
		}
		
		return documentSummary;
	}

	public DocumentSummary quiz(Resource resource, String lang, Integer numberOfQuestions) {
		log.info(String.format("%s -> %s", MistralAIChatService.class.getSimpleName(), "quiz"));
		BeanOutputConverter<DocumentQuiz> beanOutputConverter = new BeanOutputConverter<>(DocumentQuiz.class);
		String format = beanOutputConverter.getFormat();
		
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(this.quiz);
		Message systemMessage = systemPromptTemplate.createMessage(Map.of("format", format, "language", lang, "numberOfQuestions", numberOfQuestions));
		UserMessage userMessage = new UserMessage(resource);
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
		
		log.info(String.format("Calling MistralAI"));
		ChatResponse chatResponse = this.chatModel.call(prompt);
		DocumentQuiz documentQuiz = beanOutputConverter.convert(chatResponse.getResult().getOutput().getText());
		log.info(String.format("MistralAI FinishReason : %s", chatResponse.getResult().getMetadata().getFinishReason()));
		return documentQuiz;
	}
	
}
