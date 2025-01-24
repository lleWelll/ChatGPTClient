package org.ChatGPT.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ChatGPT.builders.TextRequestBuilder;
import org.ChatGPT.enums.ModelType;
import org.ChatGPT.exceptions.RequestException;
import org.ChatGPT.interfaces.Request;

import java.util.ArrayList;
import java.util.List;

public class TextRequest implements Request {
	private final ModelType modelType;
	private final Double temperature;
	private final String assistantContent;
	private final String systemContent;
	private final String userContent;
	private final Integer numOfResponsesPerRequest;

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

	private JsonObject createDefaultPrompt() {
		JsonObject prompt = new JsonObject();
		prompt.addProperty("model", this.modelType.getModel());
		prompt.addProperty("temperature", this.temperature);
		prompt.addProperty("n", this.numOfResponsesPerRequest);
		return prompt;
	}

	private JsonObject createUserContent() {
		JsonObject userContent = new JsonObject();
		userContent.addProperty("role", "user");
		userContent.addProperty("content", this.userContent);
		return userContent;
	}

	private JsonObject createSystemContent() {
		JsonObject systemContent = new JsonObject();
		systemContent.addProperty("role", "system");
		systemContent.addProperty("content", this.systemContent);
		return systemContent;
	}

	private JsonObject createAssistantContent() {
		JsonObject assistantContent = new JsonObject();
		assistantContent.addProperty("role", "assistant");
		assistantContent.addProperty("content", this.assistantContent);
		return assistantContent;
	}

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
