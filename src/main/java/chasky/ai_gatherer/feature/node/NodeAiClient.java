package chasky.ai_gatherer.feature.node;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import chasky.ai_gatherer.common.ai.LmStudioAiConfig;
import chasky.ai_gatherer.common.ai.NodeAiConfig;
import chasky.ai_gatherer.common.ai.NodeAiInterface;
import chasky.ai_gatherer.feature.node.relation.output.NodeRelationsResponse;
import jakarta.annotation.PostConstruct;

@Component
public class NodeAiClient {

    private final NodeAiInterface aiConfig;

    public NodeAiClient(LmStudioAiConfig aiConfig) {
        this.aiConfig = aiConfig;
    }

    private final Class<?> object = NodeRelationsResponse.class;

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(10))
            .build();

    private final Set<Integer> RETRYABLE_STATUS = Set.of(429, 500, 502, 503, 504);

    public NodeRelationsResponse promptAi(String prompt) throws IOException, InterruptedException {
        String requestBody = aiConfig.constructRequestBody(prompt, object);

        HttpRequest httpRequest = aiConfig.constructHttpRequest(requestBody);

        HttpResponse<String> response = sendRequest(httpRequest);

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from AI");
        }

        return aiConfig.parseResponse(response.body(),
                NodeRelationsResponse.class);
    }

    private HttpResponse<String> sendRequest(HttpRequest request)
            throws IOException, InterruptedException {
        int maxRetries = 5;
        long baseBackoffMillis = 500;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (!RETRYABLE_STATUS.contains(response.statusCode())) {
                return response;
            }

            if (attempt == maxRetries) {
                throw new IOException("Failed after retries, last status: " + response.statusCode());
            }

            long jitter = ThreadLocalRandom.current().nextLong(0, 250);
            long sleepMillis = (baseBackoffMillis * (1L << (attempt - 1))) + jitter;

            Thread.sleep(sleepMillis);
        }
        throw new IOException("Unreachable");
    }

    @PostConstruct
    private void checkKey() {
        if (System.getenv("OPENAI_API_KEY") == null || System.getenv("OPENAI_API_KEY") == "") {
            throw new IllegalStateException("API key was not loaded before construction");
        }

        System.out.println("API Key loaded successfully (first 5 chars: "
                + System.getenv("OPENAI_API_KEY").substring(0, 5) + "...)");
    }
}
