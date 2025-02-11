package org.ChatGPT;

import org.ChatGPT.exceptions.ChatGptGenerationException;
import org.ChatGPT.requests.TextRequest;
import org.ChatGPT.builders.TextRequestBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Stack;


/**
 *The Singleton {@code ChatGPTClient} class provides an interface for interacting with OpenAI's ChatGPT chat generation endpoint
 *
 * <p>
 *     To send the request use {@code sendRequestToTextEndPoint()} method. It takes 2 arguments: {@link String} ApiKey and {@link TextRequest} request.
 * </p>
 *
 * <p>
 *     Class uses API-key to send requests. API-key can be set with {@code setApiKey()} method and deleted with {@@code dropApiKey()} method.
 * </p>
 * <p>
 *     Class saves responses in stack and can be got with {@code getLastResponse()} method.
 * </p>
 *
 * @see TextRequest
 * @see TextRequestBuilder
 * @see ChatGptGenerationException
 */
public class ChatGPTClient {

	/**
	 * Field that contains single instance of ChatGPTClient class.
	 */
	private static ChatGPTClient instance;

	/**
	 * Stack that contains history of responses from ChatGPT.
	 */
	private final Stack<ChatGPTResponse> RESPONSE_HISTORY = new Stack<>();

	/**
	 * The API key for authentication with the ChatGPT API.
	 */
	private String apiKey;

	/**
	 * The URI for the ChatGPT's chat completion endpoint.
	 */
	private final URI TEXT_ENDPOINT_URI = URI.create("https://api.openai.com/v1/chat/completions");

	/**
	 * Retrieves the singleton instance of {@link ChatGPTClient}.
	 *
	 * @return The singleton instance of {@link ChatGPTClient}.
	 */
	public static ChatGPTClient getInstance() {
		if (instance == null) instance = new ChatGPTClient();
		return instance;
	}

	/**
	 * Private constructor to enforce singleton pattern.
	 */
	private ChatGPTClient() {
	}

	/**
	 * Sets the API key for the client.
	 *
	 * @param apikey The API key to be used for authentication.
	 */
	public void setApiKey(String apikey) {
		this.apiKey = apikey;
	}

	/**
	 * Clears the stored API key.
	 */
	public void dropApiKey() {
		this.apiKey = null;
	}

	/**
	 * Retrieves the most recent HTTP response from the response history.
	 *
	 * @return The last {@link HttpResponse} stored in the history.
	 * @throws java.util.EmptyStackException If the history is empty.
	 */
	public ChatGPTResponse getLastResponse() {
		return RESPONSE_HISTORY.peek();
	}

	/**
	 * Creates an HTTP request to the ChatGPT text completion endpoint.
	 *
	 * @param apiKey The API key for authentication.
	 * @param prompt The {@link TextRequest} containing the prompt to be sent.
	 * @return A configured {@link HttpRequest}.
	 */
	private HttpRequest createRequestToTextEndPoint(String apiKey, TextRequest prompt) {
		return HttpRequest.newBuilder()
				.uri(TEXT_ENDPOINT_URI)
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(prompt.getJson()))
				.build();
	}

	/**
	 * Sends a request to the ChatGPT API using the provided API key and prompt.
	 *
	 * @param apiKey The API key for authentication.
	 * @param prompt The {@link TextRequest} containing the prompt to be sent.
	 * @return The {@link ChatGPTResponse} received from the API.
	 * @throws ChatGptGenerationException If an error occurs during the request or the response indicates a failure.
	 */
	public ChatGPTResponse sendRequestToTextEndPoint(String apiKey, TextRequest prompt) {
		try {
			HttpRequest request = createRequestToTextEndPoint(apiKey, prompt);
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new ChatGptGenerationException("Error Details: " + response.body());
			}
			ChatGPTResponse res = new ChatGPTResponse(response);
			RESPONSE_HISTORY.push(res);
			return res;
		} catch (IOException | InterruptedException e) {
			throw new ChatGptGenerationException("Request error", e);
		}
	}

	/**
	 * Sends a request to the ChatGPT API using the stored API key and the provided prompt.
	 *
	 * @param prompt The {@link TextRequest} containing the prompt to be sent.
	 * @return The {@link ChatGPTResponse} received from the API.
	 * @throws ChatGptGenerationException If the API key is not set or an error occurs during the request.
	 */
	public ChatGPTResponse sendRequestToTextEndPoint(TextRequest prompt) {
		if (apiKey == null)
			throw new ChatGptGenerationException("API Key is not found: Please provide APIKey (use setApiKey() method)");
		return sendRequestToTextEndPoint(apiKey, prompt);
	}

}
