package org.ChatGPT;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.ChatGPT.exceptions.ResponseException;

import java.net.http.HttpResponse;

/**
 * Represents a response from ChatGPT, parsing the JSON response body from an HTTP request.
 * Provides various utility methods to extract relevant information from the response.
 */
public class ChatGPTResponse {

	private final JsonObject response;

	/**
	 * Constructs a ChatGPTResponse object from an HttpResponse.
	 * @param response the HTTP response containing the JSON body.
	 * @throws NullPointerException if the response is null.
	 * @throws ResponseException    if the response body is empty or blank.
	 */
	public ChatGPTResponse(HttpResponse<String> response) {
		if (response == null) throw new NullPointerException("HttpResponse<String> is null");
		else if (response.body().isEmpty() || response.body().isBlank()) throw new ResponseException("Body of HttpResponse is empty or blank");
		JsonElement element = JsonParser.parseString(response.body());
		this.response = element.getAsJsonObject();
	}

	/**
	 * Returns the full JSON response as a string.
	 * @return the full response JSON as a string.
	 */
	public String getFullResponse() {
		return response.toString();
	}

	/**
	 * Extracts the content message from the response.
	 * @return the content message as a string.
	 */
	public String getContent() {
		JsonObject choices = response.getAsJsonArray("choices").get(0).getAsJsonObject();
		JsonElement content = choices.getAsJsonObject("message").get("content");
		return content.toString();
	}

	/**
	 * Retrieves the choices array from the response.
	 * @return the choices JSON array as a string.
	 */
	public String getChoices() {
		return response.get("choices").toString();
	}

	/**
	 * Retrieves the unique identifier of the response.
	 * @return the response ID as a string.
	 */
	public String getId() {
		return response.get("id").toString();
	}

	/**
	 * Retrieves the number of tokens used for the prompt.
	 * @return the number of prompt tokens as a string.
	 */
	public String getPromptTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonElement tokens = usage.get("prompt_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of tokens used for the completion.
	 * @return the number of completion tokens as a string.
	 */
	public String getCompletionTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonElement tokens = usage.get("completion_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the total number of tokens used in the response.
	 * @return the total number of tokens as a string.
	 */
	public String getTotalTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonElement tokens = usage.get("total_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of audio tokens used in the response.
	 * @return the number of audio tokens as a string.
	 */
	public String getAudioTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonObject details = usage.get("prompt_tokens_details").getAsJsonObject();
		JsonElement tokens = details.get("audio_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of cached tokens used in the response.
	 * @return the number of cached tokens as a string.
	 */
	public String getCachedTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonObject details = usage.get("prompt_tokens_details").getAsJsonObject();
		JsonElement tokens = details.get("cached_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of reasoning tokens used in the response.
	 * @return the number of reasoning tokens as a string.
	 */
	public String getReasoningTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonObject details = usage.get("completion_tokens_details").getAsJsonObject();
		JsonElement tokens = details.get("reasoning_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of accepted prediction tokens.
	 * @return the number of accepted prediction tokens as a string.
	 */
	public String getAcceptedPredictionTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonObject details = usage.get("completion_tokens_details").getAsJsonObject();
		JsonElement tokens = details.get("accepted_prediction_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the number of rejected prediction tokens.
	 * @return the number of rejected prediction tokens as a string.
	 */
	public String getRejectedPredictionTokens() {
		JsonObject usage = response.get("usage").getAsJsonObject();
		JsonObject details = usage.get("completion_tokens_details").getAsJsonObject();
		JsonElement tokens = details.get("rejected_prediction_tokens");
		return tokens.toString();
	}

	/**
	 * Retrieves the system fingerprint associated with the response.
	 * @return the system fingerprint as a string.
	 */
	public String getFingerprint() {
		return response.get("system_fingerprint").toString();
	}

	/**
	 * Retrieves the model used for generating the response.
	 * @return the model name as a string.
	 */
	public String getModel() {
		return response.get("model").toString();
	}

	/**
	 * Retrieves the timestamp of when the response was created.
	 * @return the creation timestamp as a string.
	 */
	public String getCreated() {
		return response.get("created").toString();
	}

	/**
	 * Retrieves the type of object returned in the response.
	 * @return the object type as a string.
	 */
	public String getObject() {
		return response.get("object").toString();
	}
}
