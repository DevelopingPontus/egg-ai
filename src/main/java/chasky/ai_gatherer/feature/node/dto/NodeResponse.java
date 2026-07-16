package chasky.ai_gatherer.feature.node.dto;

import java.util.List;

public record NodeResponse(
                String subject,
                String topic,
                List<String> topicsThisDependOn,
                List<String> topicsThisEnables
        ) {

}
