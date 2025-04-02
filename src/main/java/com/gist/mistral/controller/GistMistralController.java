package com.gist.mistral.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gist.mistral.service.MistralAIChatService;
import com.gist.mistral.service.client.MistralAIClient;
import com.gist.mistral.service.client.request.MistralAIOCRDocumentRequest;
import com.gist.mistral.service.client.request.MistralAIOCRImageRequest;
import com.gist.mistral.service.client.request.MistralAIOCRPageResponse;
import com.gist.mistral.service.client.request.MistralAIOCRRequest;
import com.gist.mistral.service.client.response.MistralAIOCRResponse;
import com.gist.mistral.service.entity.DocumentSummary;
import com.gist.mistral.service.entity.DocumentSummaryParagraph;
import com.gist.mistral.service.entity.DocumentSummaryWithParagraph;

@Controller
public class GistMistralController {
	@Autowired
	private MistralAIChatService mistralAIChatService;
	
	@Autowired
	private MistralAIClient mistralAIClient;
	
    @PostMapping(value = "/ocr/pdf/url")
    public ResponseEntity<DocumentSummaryWithParagraph> ocrByPDF(@RequestParam String url, @RequestParam String lang) throws FileNotFoundException, IOException, InterruptedException {
    	MistralAIOCRRequest request = new MistralAIOCRRequest();
    	MistralAIOCRDocumentRequest document = new MistralAIOCRDocumentRequest();
    	document.setDocument_url(url);
		request.setDocument(document);
		MistralAIOCRResponse mistralAIOCRResponse = mistralAIClient.ocr(request);
		
		File markdown = new File("out/markdown_"+System.currentTimeMillis()+".md");
		List<MistralAIOCRPageResponse> pages = mistralAIOCRResponse.getPages();
		StringBuffer buffer = new StringBuffer();
		for (MistralAIOCRPageResponse page : pages) {
			FileUtils.writeStringToFile(markdown, page.getMarkdown(), Charset.forName("UTF-8"), Boolean.TRUE);
			buffer.append(page.getMarkdown());
		}
		
		InputStreamResource inputStream = new InputStreamResource(new FileInputStream(markdown));
		DocumentSummaryWithParagraph documentSummary = (DocumentSummaryWithParagraph) mistralAIChatService.summary(inputStream, lang, Boolean.TRUE);
    	return new ResponseEntity<DocumentSummaryWithParagraph>(documentSummary, HttpStatus.ACCEPTED);
    }
    
    @PostMapping(value = "/ocr/image/url")
    public ResponseEntity<MistralAIOCRResponse> ocrByImage(@RequestParam String url) throws FileNotFoundException, IOException, InterruptedException {
    	MistralAIOCRRequest request = new MistralAIOCRRequest();
    	MistralAIOCRImageRequest document = new MistralAIOCRImageRequest();
    	document.setImage_url(url);
		request.setDocument(document);
		MistralAIOCRResponse mistralAIOCRResponse = mistralAIClient.ocr(request);
    	
		File markdown = new File("markdown_"+System.currentTimeMillis()+".md");
		List<MistralAIOCRPageResponse> pages = mistralAIOCRResponse.getPages();
		for (MistralAIOCRPageResponse page : pages) {
			FileUtils.writeStringToFile(markdown, page.getMarkdown(), Charset.forName("UTF-8"), Boolean.TRUE);
		}
		
    	return new ResponseEntity<MistralAIOCRResponse>(mistralAIOCRResponse, HttpStatus.ACCEPTED);
    }
    
    @PostMapping(value = "/summarize")
    public String summarize(@RequestParam String url, @RequestParam String lang, @RequestParam(defaultValue = "false", required = false) Boolean withParagraph, Model model) throws FileNotFoundException, IOException, InterruptedException {
    	MistralAIOCRRequest request = new MistralAIOCRRequest();
    	MistralAIOCRDocumentRequest document = new MistralAIOCRDocumentRequest();
    	document.setDocument_url(url);
		request.setDocument(document);
		MistralAIOCRResponse mistralAIOCRResponse = mistralAIClient.ocr(request);
		
		File markdown = new File("out/markdown_"+System.currentTimeMillis()+".md");
		List<MistralAIOCRPageResponse> pages = mistralAIOCRResponse.getPages();
		StringBuffer buffer = new StringBuffer();
		for (MistralAIOCRPageResponse page : pages) {
			FileUtils.writeStringToFile(markdown, page.getMarkdown(), Charset.forName("UTF-8"), Boolean.TRUE);
			buffer.append(page.getMarkdown());
		}
		
    	//File markdown = new File("out/markdown_1743615170534.md");
		InputStreamResource inputStream = new InputStreamResource(new FileInputStream(markdown));
		DocumentSummary documentSummary = mistralAIChatService.summary(inputStream, lang, withParagraph);
		//DocumentSummary documentSummary = mockDocument();
		
		model.addAttribute("documentSummary", documentSummary);
        return withParagraph ? "summary_with_paragraphs" : "summary";
    }

	@SuppressWarnings("unused")
	private DocumentSummaryWithParagraph mockDocument() {
		DocumentSummaryWithParagraph documentSummary = new DocumentSummaryWithParagraph();
		documentSummary.setShortDescription("L'ode Il Cinque Maggio è un componimento di Alessandro Manzoni scritto in memoria di Napoleone Bonaparte, pubblicato nel 1823 in Italia senza l'autorizzazione dell'autore. La poesia è strutturata in 18 strofe composte da 6 versi di settenari, ciascuna delle quali è composta da due quatriemoli e due terzettoni. Il tema centrale dell'ode è la morte di Napoleone Bonaparte e la sua eroica vita. Manzoni riconosce la grandezza dell'uomo, ma alla sua memoria aggiunge un'interpretazione cristiana e divina, che gli attribuisce un ruolo di protagonista nella storia. Il poeta descrive la reazione generale all'annuncio della morte di Napoleone, la cui grandezza e crudele fine hanno lasciato un profondo segno nell'immaginario collettivo europeo. In particolare, Manzoni si riferisce alla morte di Napoleone come a quello che rappresenta l'idea di un «uomo fatale». La morte di Napoleone è vista come un punto di svolta nella storia, che rappresenta la fine di un'era e l'inizio di una nuova. L'ode conclude con una preghiera per la pace e la speranza in un futuro migliore, in cui si possa superare gli errori del passato.");
		
		List<DocumentSummaryParagraph> paragraphs = new ArrayList<>();
		DocumentSummaryParagraph paragraph = new DocumentSummaryParagraph();
		paragraph.setTitle("Vicenda terrena e disegno divino");
		paragraph.setText("Il motivo ispiratore dell'ode è la rievocazione delle vicende terrene di Napoleone (l'ascesa e la conquista del potere, la grandezza e la gloria, la sconfitta e la morte), a cui si accompagna la riflessione cristiana.");
		paragraphs.add(paragraph );
		paragraphs.add(paragraph );
		documentSummary.setParagraphs(paragraphs );
		return documentSummary;
	}
    
}
