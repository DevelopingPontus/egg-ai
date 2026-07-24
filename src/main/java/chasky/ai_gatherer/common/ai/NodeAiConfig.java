package chasky.ai_gatherer.common.ai;

import java.net.URI;
import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import chasky.ai_gatherer.common.util.MyJsonSchemaGenerator;

@Configuration
public class NodeAiConfig {
  private static final Logger log = LoggerFactory.getLogger(NodeAiConfig.class);

  MyJsonSchemaGenerator generator = new MyJsonSchemaGenerator("node");

  private String format;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
  private final String API_URL = "https://api.openai.com/v1/responses";
  private final String model = "gpt-4o";
  // private final String OPENAI_API_KEY = "not-needed";
  // private final String API_URL = "http://127.0.0.1:1234/v1/responses";
  // private final String model = "google/gemma-4-e4b";

  private String system = "";
  private Float temperature = 0f;

  public NodeAiConfig() {
  }

  public NodeAiConfig(String system, Float temperature) {
    this.system = system;
    this.temperature = temperature;
  }

  public String constructRequestBody(String prompt, Class object) {
    if (format == null) {
      format = generator.generateSchema(object);
    }
    System.out.println(format);

    return String.format("""
            {
              "model": "%s",
              "input": [
                { "role": "system", "content": "%s" },
                { "role": "user", "content": "%s" }
                ],
                "temperature": %s,
                "text": %s
        }
                """, model, system, prompt, temperature, format);
  }

  public HttpRequest constructHttpRequest(String requestBody) {
    return HttpRequest.newBuilder()
        .uri(URI.create(API_URL))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + OPENAI_API_KEY)
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .timeout(java.time.Duration.ofSeconds(30))
        .build();
  }

  public <T> T parseResponse(String responseBody,
      Class<T> responseType) throws JsonMappingException, JsonProcessingException {
    JsonNode root = objectMapper.readTree(responseBody);

    String text = root.path("output")
        .get(0)
        .path("content")
        .get(0)
        .path("text")
        .asText();

    return objectMapper.readValue(text, responseType);
  }

}
