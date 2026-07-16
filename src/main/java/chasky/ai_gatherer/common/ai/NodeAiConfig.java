package chasky.ai_gatherer.common.ai;

import java.net.URI;
import java.net.http.HttpRequest;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

@Configuration
public class NodeAiConfig {

  private final ObjectMapper objectMapper = new ObjectMapper();
  JsonSchemaGenerator schemaGenerator = new JsonSchemaGenerator(objectMapper);
  
  private String format;
  private final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
  private final String API_URL = "https://api.openai.com/v1/responses";
  private final String model = "gpt-4o";
  
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
      format = generateFormat(object);
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
                "text": {
                  "format": {
                    "type": "json_schema",
                    "name": "response",
                    "schema": %s
                  }
                }
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

  private String generateFormat(Class object) {
    try {
      JsonSchema schema = schemaGenerator.generateSchema(object);

      
      ObjectNode schemaNode = objectMapper.valueToTree(schema);
      ArrayNode required = schemaNode.putArray("required");
      schemaNode.get("properties").fieldNames().forEachRemaining(required::add);

      schemaNode.put("additionalProperties", false);

      schemaNode.put("strict", true);


      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaNode);
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate schema");
    }
  }
}
