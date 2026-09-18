package chasky.ai_gatherer.feature.function.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
public class FunctionDTO {
    private String reasoning;
    private String name;
}
