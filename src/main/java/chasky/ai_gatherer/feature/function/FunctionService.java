package chasky.ai_gatherer.feature.function;

import java.io.IOException;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

@Service
public class FunctionService {
    private final FunctionServiceHelpers<FunctionResponse> helpers;

    public FunctionService(FunctionServiceHelpers<FunctionResponse> helpers) {
        this.helpers = helpers;
    }

    public FunctionEntity generateNewFunction(String prompt)
            throws IOException, InterruptedException {
        FunctionResponse response = helpers.promptAi(prompt);
        FunctionEntity rootFunction = helpers.saveFunctionResponseAndReturnParent(response);

        helpers.itterateToAResponseLayerOf(rootFunction.getHelperFunctions(), 1);

        rootFunction = helpers.functionRepo.fetchRoot(rootFunction.getId());
        rootFunction = helpers.getTree(rootFunction.getId());
        return rootFunction;
    }

}
