package org.ChatGPT.builders;


import org.ChatGPT.requests.TextRequest;
import org.ChatGPT.enums.ModelType;
import org.ChatGPT.exceptions.RequestBuildException;
import org.ChatGPT.inrefaces.Builder;

public class TextRequestBuilder implements Builder {
	private ModelType modelType;
	private Double temperature;
	private String assistantContent;
	private String systemContent;
	private String userContent;
	private Integer n;

	public ModelType getModelType() {
		return modelType;
	}

	public Double getTemperature() {
		return temperature;
	}

	public String getAssistantContent() {
		return assistantContent;
	}

	public String getSystemContent() {
		return systemContent;
	}

	public String getUserContent() {
		return userContent;
	}

	public Integer getN() {
		return n;
	}

	public TextRequestBuilder() {
	}

	public TextRequestBuilder setModel(ModelType modelType) {
		this.modelType = modelType;
		return this;
	}

	public TextRequestBuilder setTemperature(Double temperature) {
		if (temperature <= 0) throw new RequestBuildException("Invalid temperature value: must be greater than 0");
		else if (temperature > 2) throw new RequestBuildException("Invalid temperature value: must not exceed 2");
		this.temperature = temperature;
		return this;
	}

	public TextRequestBuilder setN(Integer n) {
		if (n < 1) throw new RequestBuildException("Invalid 'n' value: must be at least 1");
		this.n = n;
		return this;
	}

	public TextRequestBuilder setUserContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("User content cannot be blank. Please provide a valid prompt");
		this.userContent = prompt;
		return this;
	}

	public TextRequestBuilder setAssistantContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("Assistant content cannot be blank. Please provide a valid response");
		this.assistantContent = prompt;
		return this;
	}

	public TextRequestBuilder setSystemContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("System content cannot be blank. Please provide valid system instructions");
		this.systemContent = prompt;
		return this;
	}

	@Override
	public TextRequest build() {
		return new TextRequest(this);
	}
}
