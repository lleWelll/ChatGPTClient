package org.ChatGPT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

@ExtendWith(MockitoExtension.class)
public class ChatGPTResponseTest {

	@Mock
	private HttpResponse<String> httpResponse;

	private ChatGPTResponse response;

	private final String responseText = "{\"id\":\"chatcmpl-AzkGLeWZqZHLOinIx34q6ueEOAHN7\",\"object\":\"chat.completion\",\"created\":1739279205,\"model\":\"gpt-4o-2024-08-06\",\"choices\":[{\"index\":0,\"message\":{\"role\":\"assistant\",\"content\":\"This is Test!\",\"refusal\":null},\"logprobs\":null,\"finish_reason\":\"stop\"}],\"usage\":{\"prompt_tokens\":9,\"completion_tokens\":10,\"total_tokens\":19,\"prompt_tokens_details\":{\"cached_tokens\":1,\"audio_tokens\":2},\"completion_tokens_details\":{\"reasoning_tokens\":3,\"audio_tokens\":33,\"accepted_prediction_tokens\":4,\"rejected_prediction_tokens\":5}},\"service_tier\":\"default\",\"system_fingerprint\":\"fp_50cad350e4\"}";


	@BeforeEach
	public void init() {
		Mockito.when(httpResponse.body()).thenReturn(responseText);
		response = new ChatGPTResponse(httpResponse);
	}

	@Test
	public void getFullAnswer_returnsCorrectAnswer() {
		String result = response.getFullResponse();
		Assertions.assertEquals(responseText, result);
	}

	@Test
	public void getContent_returnsCorrectAnswer() {
		String result = response.getContent();
		String expected = "\"This is Test!\"";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getChoices_returnsCorrectChoices() {
		String result = response.getChoices();
		String expected = "[{\"index\":0,\"message\":{\"role\":\"assistant\",\"content\":\"This is Test!\",\"refusal\":null},\"logprobs\":null,\"finish_reason\":\"stop\"}]";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getId_returnsCorrectId() {
		String result = response.getId();
		String expected = "\"chatcmpl-AzkGLeWZqZHLOinIx34q6ueEOAHN7\"";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getModel_returnsCorrectModel() {
		String result = response.getModel();
		String expected = "\"gpt-4o-2024-08-06\"";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getCreated_returnsCorrectCreated() {
		String result = response.getCreated();
		String expected = "1739279205";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getObject_returnsCorrectObject() {
		String result = response.getObject();
		String expected = "\"chat.completion\"";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getPromptTokens_returnsCorrectNumberOfTokens() {
		String result = response.getPromptTokens();
		String expected = "9";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getCompletionTokens_returnsCorrectNumberOfTokens() {
		String result = response.getCompletionTokens();
		String expected = "10";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getTotalTokens_returnsCorrectNumberOfTokens() {
		String result = response.getTotalTokens();
		String expected = "19";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getCachedTokens_returnsCorrectNumberOfTokens() {
		String result = response.getCachedTokens();
		String expected = "1";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getAudioTokens_returnsCorrectNumberOfTokens() {
		String result = response.getAudioTokens();
		String expected = "2";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getReasoningTokens_returnsCorrectNumberOfTokens() {
		String result = response.getReasoningTokens();
		String expected = "3";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getAcceptedPredictionTokens_returnsCorrectNumberOfTokens() {
		String result = response.getAcceptedPredictionTokens();
		String expected = "4";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getRejectedPredictionTokens_returnsCorrectNumberOfTokens() {
		String result = response.getRejectedPredictionTokens();
		String expected = "5";
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void getFingerprint_returnsCorrectFingerprint() {
		String result = response.getFingerprint();
		String expected = "\"fp_50cad350e4\"";
		Assertions.assertEquals(expected, result);
	}
}
