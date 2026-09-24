package chasky.ai_gatherer.feature.function;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

@RestController
@RequestMapping("/api/v1/functions")
public class FunctionController {

    private final FunctionService functionService;

    public FunctionController(FunctionService functionService) {
        this.functionService = functionService;
    }

    @PostMapping
    public FunctionEntity generateNewFunction(String prompt)
            throws IOException, InterruptedException {
        return functionService.generateNewFunction(prompt);
    }
}
