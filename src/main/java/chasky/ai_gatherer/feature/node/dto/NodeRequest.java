package chasky.ai_gatherer.feature.node.dto;

import jakarta.validation.constraints.Size;

public record NodeRequest(
        @Size(max = 200, min = 1)String descriptionOfTopic
) {
    
}
