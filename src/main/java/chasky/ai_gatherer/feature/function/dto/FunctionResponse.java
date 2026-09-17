package chasky.ai_gatherer.feature.function.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FunctionResponse {
    private String reasoning;
    private FunctionDTO function;
    private List<FunctionDTO> helperFunctions;
}
