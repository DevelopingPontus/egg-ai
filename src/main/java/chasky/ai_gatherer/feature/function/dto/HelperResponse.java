package chasky.ai_gatherer.feature.function.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelperResponse {
    private String reasoning;
    private List<FunctionDTO> helperFunctions;
}
