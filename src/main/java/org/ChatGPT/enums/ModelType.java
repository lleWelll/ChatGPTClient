package org.ChatGPT.enums;


//https://platform.openai.com/docs/models#gpt-4o-mini
public enum ModelType {

	GPT4o("gpt-4o"),
	GPT4omini("gpt-4o-mini"),
	o1("o1"),
	o1mini("o1-mini"),
	Dalle3("dall-e-3"),
	Dalle2("dall-e-2");

	private final String model;

	ModelType(String model) {
		this.model = model;
	}
	public String getModel() {
		return model;
	}
}
