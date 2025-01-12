package org.ChatGPT;

import org.ChatGPT.exceptions.ChatGptGenerationException;
import org.ChatGPT.requests.TextRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Stack;

public class ChatGPClient {

	private static ChatGPClient instance;

	private final Stack<HttpResponse<String>> RESPONSE_HISTORY = new Stack<>();

	private String apiKey;

	private final URI TEXT_ENDPOINT_URI = URI.create("https://api.openai.com/v1/chat/completions");

	public static ChatGPClient getInstance() {
		if (instance == null) instance = new ChatGPClient();
		return instance;
	}

	private ChatGPClient() {
	}

	public void setApiKey(String apikey) {
		this.apiKey = apikey;
	}

	public void dropApiKey() {
		this.apiKey = null;
	}

	public HttpResponse<String> getLastResponse() {
		return RESPONSE_HISTORY.peek();
	}

	private HttpRequest createRequestToTextEndPoint(String apiKey, TextRequest prompt) {
		return HttpRequest.newBuilder()
				.uri(TEXT_ENDPOINT_URI)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(prompt.getJson()))
				.build();
	}

	private HttpRequest createRequestToTextEndPoint(TextRequest prompt) {
		if (apiKey == null)
			throw new ChatGptGenerationException("API Key is not found: Please provide APIKey (use setApiKey() method)");
		return HttpRequest.newBuilder()
				.uri(TEXT_ENDPOINT_URI)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(prompt.getJson()))
				.build();
	}

	public HttpResponse<String> sendResponse(String apiKey, TextRequest prompt) {
		try {
			HttpRequest request = createRequestToTextEndPoint(apiKey, prompt);
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new ChatGptGenerationException("Error Details: " + response.body());
			}
			RESPONSE_HISTORY.push(response);
			return response;
		} catch (IOException | InterruptedException e) {
			throw new ChatGptGenerationException("Request error", e);
		}
	}

	public HttpResponse<String> sendResponse(TextRequest prompt) {
		if (apiKey == null)
			throw new ChatGptGenerationException("API Key is not found: Please provide APIKey (use setApiKey() method)");
		return sendResponse(apiKey, prompt);
	}

}
