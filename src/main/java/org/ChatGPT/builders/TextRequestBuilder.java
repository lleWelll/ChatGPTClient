package org.ChatGPT.builders;


import org.ChatGPT.requests.TextRequest;
import org.ChatGPT.enums.ModelType;
import org.ChatGPT.exceptions.RequestBuildException;
import org.ChatGPT.interfaces.Builder;

/**
 * A builder class to facilitate the construction of {@link TextRequest} objects.
 * <p>
 * Class allows setting parameters for the request, such as the model type, temperature, user content,
 * assistant content, system content, and the number of responses per request. It provides a fluent API for chaining
 * method calls to customize the request and validate the inputs before constructing the final {@link TextRequest} object.
 * </p>
 * <p>
 * The builder ensures that the parameters are valid by throwing {@link RequestBuildException} for any invalid values.
 * </p>
 *
 * @see org.ChatGPT.interfaces.Builder
 * @see TextRequest
 * @see RequestBuildException
 * @see ModelType
 */
public class TextRequestBuilder implements Builder {

	/**
	 * The model type to use for generating responses. Optional, by default - {@code ModelType.GPT4omini}
	 * @see ModelType
	 */
	private ModelType modelType;

	/**
	 * The temperature to control the randomness of the model's responses. Optional, by default - 1
	 */
	private Double temperature;

	/**
	 * The content of the assistant's response. Optional, by default - null
	 */
	private String assistantContent;

	/**
	 * The content of the system message that provides instructions. Optional, by default - null
	 */
	private String systemContent;

	/**
	 * The user-provided prompt content that guides the model's response. necessary param.
	 */
	private String userContent;

	/**
	 * The number of responses to generate for each request.
	 */
	private Integer numOfResponsesPerRequest;

	/**
	 * Gets the model type set for the request.
	 *
	 * @return The model type.
	 */
	public ModelType getModelType() {
		return modelType;
	}

	/**
	 * Gets the temperature set for the request.
	 *
	 * @return The temperature value.
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * Gets the assistant content set for the request.
	 *
	 * @return The assistant content.
	 */
	public String getAssistantContent() {
		return assistantContent;
	}

	/**
	 * Gets the system content set for the request.
	 *
	 * @return The system content.
	 */
	public String getSystemContent() {
		return systemContent;
	}

	/**
	 * Gets the user content set for the request.
	 *
	 * @return The user content.
	 */
	public String getUserContent() {
		return userContent;
	}

	/**
	 * Gets the number of responses per request set for the request.
	 *
	 * @return The number of responses.
	 */
	public Integer getNumOfResponses() {
		return numOfResponsesPerRequest;
	}

	/**
	 * Default constructor for creating a new instance of the builder.
	 */
	public TextRequestBuilder() {
	}

	/**
	 * Sets the model type to use for generating responses.
	 *
	 * @param modelType The model type (e.g., GPT-4o, GPT-4omini, o1, o1mini).
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException if inserted model is not compatible for chat generation endpoint.
	 */
	public TextRequestBuilder setModel(ModelType modelType) {
		if (modelType.equals(ModelType.Dalle2) || modelType.equals(ModelType.Dalle3)) {
			throw new RequestBuildException(modelType.getModel() + " model is not compatible with TextRequest. Please, change model to GPT4 or another");
		}
		this.modelType = modelType;
		return this;
	}

	/**
	 * Sets the temperature for controlling the randomness of the model's responses.
	 * The temperature must be greater than 0 and not exceed 2.
	 *
	 * @param temperature The temperature value.
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException If the temperature is not in the valid range.
	 */
	public TextRequestBuilder setTemperature(Double temperature) {
		if (temperature <= 0) throw new RequestBuildException("Invalid temperature value: must be greater than 0");
		else if (temperature > 2) throw new RequestBuildException("Invalid temperature value: must not exceed 2");
		this.temperature = temperature;
		return this;
	}

	/**
	 * Sets the number of responses to generate per request.
	 * The value must be at least 1.
	 *
	 * @param numOfResponsesPerRequest The number of responses.
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException If the number of responses is less than 1.
	 */
	public TextRequestBuilder setNumOfResponses(Integer numOfResponsesPerRequest) {
		if (numOfResponsesPerRequest < 1) throw new RequestBuildException("Invalid 'n' value: must be at least 1");
		this.numOfResponsesPerRequest = numOfResponsesPerRequest;
		return this;
	}

	/**
	 * Sets the user content (the prompt) for the request.
	 * The content must not be blank.
	 *
	 * @param prompt The user content (prompt).
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException If the user content is blank.
	 */
	public TextRequestBuilder setUserContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("User content cannot be blank. Please provide a valid prompt");
		this.userContent = prompt;
		return this;
	}

	/**
	 * Sets the assistant content for the request.
	 * The content must not be blank.
	 *
	 * @param prompt The assistant content.
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException If the assistant content is blank.
	 */
	public TextRequestBuilder setAssistantContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("Assistant content cannot be blank. Please provide a valid response");
		this.assistantContent = prompt;
		return this;
	}

	/**
	 * Sets the system content (instructions) for the request.
	 * The content must not be blank.
	 *
	 * @param prompt The system content.
	 * @return The current builder instance for chaining.
	 * @throws RequestBuildException If the system content is blank.
	 */
	public TextRequestBuilder setSystemContent(String prompt) {
		if (prompt.isBlank() || prompt.isEmpty())
			throw new RequestBuildException("System content cannot be blank. Please provide valid system instructions");
		this.systemContent = prompt;
		return this;
	}

	/**
	 * Builds and returns a new {@link TextRequest} instance with the current configuration.
	 *
	 * @return A new {@link TextRequest} object.
	 */
	@Override
	public TextRequest build() {
		return new TextRequest(this);
	}
}
