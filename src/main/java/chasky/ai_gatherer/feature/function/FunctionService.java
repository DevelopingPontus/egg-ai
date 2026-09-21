package chasky.ai_gatherer.feature.function;

import java.io.IOException;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

@Service
public class FunctionService {
    private final FunctionServiceHelpers helpers;

    public FunctionService(FunctionServiceHelpers helpers) {
        this.helpers = helpers;
    }

    public FunctionEntity generateNewFunction(String prompt)
            throws IOException, InterruptedException {
        FunctionResponse response = helpers.functionClient.promptAi(prompt,
                "You are making plans in the style of java programming. For example, painting might be the function and prepareToPaint might be one of the sub goals. You are tasked with defining the function for the querys goal and it's helper functions.");

        FunctionEntity rootFunction = helpers.saveFunctionResponseAndReturnParent(response);

        helpers.itterateToAResponseLayerOf(rootFunction.getHelperFunctions(), 1);

        // rootFunction = helpers.functionRepo.findById(rootFunction.getId()).get();
        rootFunction = helpers.getTree(rootFunction.getId());
        return rootFunction;
    }

}
