package chasky.ai_gatherer.feature.category.dto;

import java.util.List;

public record CategoryResponse(
        String goal,
        CategoryResponse finalStep,
        List<CategoryResponse> stepsToReachGoal) {

}
