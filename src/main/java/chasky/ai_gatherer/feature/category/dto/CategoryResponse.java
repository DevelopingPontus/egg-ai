package chasky.ai_gatherer.feature.category.dto;

import java.util.List;

public record CategoryResponse(
        String subject,
        List<String> subjectsThisDependOn,
        List<String> subjectsThisEnables) {

}
