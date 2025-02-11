package org.ChatGPT.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ChatGPT.builders.TextRequestBuilder;
import org.ChatGPT.enums.ModelType;
import org.ChatGPT.exceptions.RequestException;
import org.ChatGPT.interfaces.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a request to the ChatGPT API with user, assistant, and system messages.
 * <p>Class uses Google GSON library to convert all params to JSON format</p>
 *
 * @see TextRequestBuilder
 */
public class TextRequest implements Request {

	/**
	 * The model type used for generating responses.
	 */
	private final ModelType modelType;

	/**
	 * The temperature parameter controlling the randomness of the response.
	 */
	private final Double temperature;

	/**
	 * The content of the assistant's message, if provided.
	 */
	private final String assistantContent;

	/**
	 * The content of the system's message, if provided.
	 */
	private final String systemContent;

	/**
	 * The content of the user's prompt. This is main request, This parameter is necessary.
	 */
	private final String userContent;

	/**
	 * The number of responses to generate for each request. (In ChatGPT documentation, this parameter called 'n')
	 */
	private final Integer numOfResponsesPerRequest;

	/**
	 * Constructs a new {@link TextRequest} using the provided builder.
	 *
	 * @param textRequestBuilder The builder containing the necessary information for the request.
	 * @throws RequestException If the userContent is null, empty, or blank.
	 */
	public TextRequest(TextRequestBuilder textRequestBuilder) {
		this.modelType = textRequestBuilder.getModelType() != null ? textRequestBuilder.getModelType() : ModelType.GPT4omini;
		this.temperature = textRequestBuilder.getTemperature() != null ? textRequestBuilder.getTemperature() : 1;
		this.numOfResponsesPerRequest = textRequestBuilder.getNumOfResponses() != null ? textRequestBuilder.getNumOfResponses() : 1;
		this.userContent = textRequestBuilder.getUserContent();
		this.systemContent = textRequestBuilder.getSystemContent();
		this.assistantContent = textRequestBuilder.getAssistantContent();

		if (userContent == null)
			throw new RequestException("Invalid request: 'userContent' must not be null, empty or blank. Provide a valid prompt");
	}

	/**
	 * Creates the default prompt in Json, which includes the model, temperature, and number of responses.
	 *
	 * @return A {@link JsonObject} representing the default prompt.
	 */
	private JsonObject createDefaultPrompt() {
		JsonObject prompt = new JsonObject();
		prompt.addProperty("model", this.modelType.getModel());
		prompt.addProperty("temperature", this.temperature);
		prompt.addProperty("n", this.numOfResponsesPerRequest);
		return prompt;
	}

	/**
	 * Creates the user message in the JSON format.
	 *
	 * @return A {@link JsonObject} representing the user message.
	 */
	private JsonObject createUserContent() {
		JsonObject userContent = new JsonObject();
		userContent.addProperty("role", "user");
		userContent.addProperty("content", this.userContent);
		return userContent;
	}

	/**
	 * Creates the system message in the Json format, if provided.
	 *
	 * @return A {@link JsonObject} representing the system message.
	 */
	private JsonObject createSystemContent() {
		JsonObject systemContent = new JsonObject();
		systemContent.addProperty("role", "system");
		systemContent.addProperty("content", this.systemContent);
		return systemContent;
	}

	/**
	 * Creates the assistant message in the JSON format, if provided.
	 *
	 * @return A {@link JsonObject} representing the assistant message.
	 */
	private JsonObject createAssistantContent() {
		JsonObject assistantContent = new JsonObject();
		assistantContent.addProperty("role", "assistant");
		assistantContent.addProperty("content", this.assistantContent);
		return assistantContent;
	}

	/**
	 * Converts the {@link TextRequest} object to its JSON representation.
	 * This includes the model type, temperature, number of responses, and messages (user, system, assistant).
	 *
	 * @return A JSON string representing the {@link TextRequest}.
	 */
	@Override
	public String getJson() {
		JsonObject prompt = createDefaultPrompt();
		List<JsonObject> messages = new ArrayList<>();
		messages.add(createUserContent());
		if (systemContent != null) messages.add(createSystemContent());
		if (assistantContent != null) messages.add(createAssistantContent());
		prompt.add("messages", new Gson().toJsonTree(messages.toArray()));
		return prompt.toString();
	}
}
