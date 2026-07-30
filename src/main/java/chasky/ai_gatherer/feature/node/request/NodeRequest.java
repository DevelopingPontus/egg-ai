package chasky.ai_gatherer.feature.node.request;

import jakarta.validation.constraints.Size;

public record NodeRequest(
        @Size(max = 400, min = 1)String descriptionOfTopic
) {
    
}
