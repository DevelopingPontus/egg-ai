package chasky.ai_gatherer.feature.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.stereotype.Component;

import chasky.ai_gatherer.feature.function.dto.FunctionDTO;
import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;
import chasky.ai_gatherer.feature.function.util.FunctionAiClient;
import chasky.ai_gatherer.feature.function.util.HelpersAiClient;

// The helpers of the service are moved here so they can be public for testing visibility and still only be useable by service.
@Component 
public class FunctionServiceHelpers {

    public final FunctionRepo functionRepo;
    public final FunctionAiClient functionClient;
    public final HelpersAiClient helpersAiClient;
    
    public FunctionServiceHelpers(FunctionRepo functionRepo, FunctionAiClient functionClient,
            HelpersAiClient helpersAiClient) {
        this.functionRepo = functionRepo;
        this.functionClient = functionClient;
        this.helpersAiClient = helpersAiClient;
    }

    // Expands the tree of linked functions to a the depth of n.
    // It collects all the helper functions of current tree depth responses to then
    // expand on each helper function to the depth of n.
    public void itterateToAResponseLayerOf(FunctionEntity rootFunction, int n)
            throws IOException, InterruptedException {
        List<FunctionEntity> itterate = rootFunction.getHelperFunctions();
        for (int i = 0; i < n; i++) {
            List<FunctionEntity> toItterate = new ArrayList<>();
            for (FunctionEntity function : itterate) {
                toItterate.addAll(expandOnHelperFunction(function));
            }
            itterate = toItterate;
        }
    }

    // Expands each helper function of parameter function.
    // Returns list of helper functions as functions with helper functions.
    public List<FunctionEntity> expandOnHelperFunction(FunctionEntity rootEntity)
            throws IOException, InterruptedException {
        FunctionEntity helperRoot = new FunctionEntity();
        List<FunctionEntity> toItterate = new ArrayList<>();
        for (FunctionEntity helper : rootEntity.getHelperFunctions()) {
            FunctionResponse response = functionClient.promptAi(helper.toString(),
                    "You are making plans in the style of java programming. For example, painting might be the function and prepareToPaint might be one of the sub goals. You are tasked with defining the helper functions.");

            helperRoot = saveFunctionResponseAndReturnParent(response);
            toItterate.add(helperRoot);
        }

        return toItterate;
    }
    // // Might swap to this version to save response tokens. FastXML problems for
    // now.
    // private List<FunctionEntity> expandOnHelpers(FunctionEntity rootEntity)
    // throws IOException, InterruptedException {
    // FunctionEntity helperRoot = new FunctionEntity();
    // List<FunctionEntity> toItterate = new ArrayList<>();
    // for (FunctionEntity helper : rootEntity.getHelperFunctions()) {
    // HelperResponse helpersResponse = helpersAiClient.promptAi(helper.toString(),
    // "You are making plans in the style of java programming. For example, painting
    // might be the function and prepareToPaint might be one of the sub goals. You
    // are tasked with defining the helper functions.");
    // FunctionResponse response = combineForFunctionResponse(helper,
    // helpersResponse);
    // helperRoot = saveFunctionsAndReturnParent(response);
    // toItterate.add(helperRoot);
    // }

    // return toItterate;
    // }
    // private FunctionResponse combineForFunctionResponse(FunctionEntity root,
    // HelperResponse helpers) {
    // FunctionResponse response = new FunctionResponse();
    // response.setReasoning(helpers.getReasoning());
    // response.setFunction(toDTO(root));
    // response.setHelperFunctions(helpers.getHelperFunctions());
    // return response;
    // }

    // private FunctionEntity saveFunctionsAndReturnParent(FunctionResponse
    // response) {
    // List<FunctionEntity> children = toEntities(response.getHelperFunctions());
    // children = functionRepo.saveAll(children);
    // FunctionEntity parent = toEntity(response.getFunction());
    // parent.setHelperFunctions(children);
    // parent = functionRepo.save(parent);

    // return parent;
    // }

    @VisibleForTesting
    public FunctionEntity saveFunctionResponseAndReturnParent(FunctionResponse response) {
        FunctionEntity parent = toEntity(response.getFunction());

        List<FunctionEntity> children = toEntities(response.getHelperFunctions());

        // 1. Set the parent reference on the children
        children.forEach(child -> child.setParent(parent));

        // 2. Save the parent first
        FunctionEntity finalParent = functionRepo.save(parent);

        // 3. Save the children (this will link them to the saved parent)
        functionRepo.saveAll(children);
        return finalParent;
    }

    public void setRelations(FunctionEntity parent, List<FunctionEntity> children) {

    }


    public List<FunctionEntity> toEntities(List<FunctionDTO> DTOs) {
        List<FunctionEntity> entities = new ArrayList<>();
        for (FunctionDTO DTO : DTOs) {
            FunctionEntity entity = toEntity(DTO);
            entities.add(entity);
        }
        return entities;
    }

    public FunctionEntity toEntity(FunctionDTO DTO) {
        FunctionEntity entity = new FunctionEntity();
        entity.setName(DTO.getName());
        entity.setReasoning(DTO.getReasoning());
        entity.setHelperFunctions(new ArrayList<>());
        return entity;
    }

    public List<FunctionDTO> toDTOs(List<FunctionEntity> entities) {
        List<FunctionEntity> entitiesForThisLoop = entities;
        List<FunctionDTO> DTOs = new ArrayList<>();
        for (FunctionEntity entity : entitiesForThisLoop) {
            FunctionDTO DTO = toDTO(entity);
            DTOs.add(DTO);
        }
        return DTOs;
    }

    public FunctionDTO toDTO(FunctionEntity entity) {
        FunctionDTO DTO = new FunctionDTO();
        DTO.setReasoning(entity.getReasoning());
        DTO.setName(entity.getName());
        return DTO;
    }

}
