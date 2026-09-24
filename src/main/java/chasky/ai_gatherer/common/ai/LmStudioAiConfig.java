package chasky.ai_gatherer.common.ai;

import java.net.URI;
import java.net.http.HttpRequest;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import chasky.ai_gatherer.common.util.MyJsonSchemaGeneratorForLmStudio;

@Configuration
public class LmStudioAiConfig implements AiConfigInterface {

  MyJsonSchemaGeneratorForLmStudio generator = new MyJsonSchemaGeneratorForLmStudio("nodeTree");

  private String format;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final String OPENAI_API_KEY = "not-needed";
  private final String API_URL = "http://127.0.0.1:1234/v1/chat/completions";
  private final String model = "qwen/qwen3.5-9b";

  private Float temperature = 0f;

  public LmStudioAiConfig() {
  }

  public LmStudioAiConfig(String system, Float temperature) {
    // this.system = system;
    this.temperature = temperature;
  }

  public String constructRequestBody(String prompt, String systemPrompt, Class<?> object) {
    if (format == null) {
      format = generator.generateSchema(object);
    }

    return String.format("""
            {
              "model": "%s",
              "messages": [
                { "role": "system", "content": "%s" },
                { "role": "user", "content": "%s" }
                ],
                "temperature": %s,
                "response_format": %s
        }
                """, model, systemPrompt, prompt, temperature, format);
  }

  public HttpRequest constructHttpRequest(String requestBody) {
    return HttpRequest.newBuilder()
        .uri(URI.create(API_URL))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + OPENAI_API_KEY)
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        // .timeout(java.time.Duration.ofSeconds(520))
        .build();
  }

  public <T> T parseResponse(String responseBody,
      Class<T> responseType) throws JsonMappingException, JsonProcessingException {
    JsonNode root = objectMapper.readTree(responseBody);
    
    String text = root.path("choices")
    .get(0)
    .path("message")
    .path("content")
    .asText();
    
    return objectMapper.readValue(text, responseType);
  }

}
