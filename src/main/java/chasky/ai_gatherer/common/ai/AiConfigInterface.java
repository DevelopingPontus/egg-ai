package chasky.ai_gatherer.common.ai;

import java.net.http.HttpRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AiConfigInterface {

    String constructRequestBody(String prompt, String systemPrompt, Class<?> object);

    HttpRequest constructHttpRequest(String requestBody);

    <T> T parseResponse(String responseBody,
            Class<T> responseType) throws JsonMappingException, JsonProcessingException;

}