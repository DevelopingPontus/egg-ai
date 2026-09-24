package chasky.ai_gatherer.feature.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import chasky.ai_gatherer.feature.function.dto.FunctionDTO;
import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;
import chasky.ai_gatherer.feature.function.util.AiClient;

// The helpers of the service are moved here so they can be public for testing visibility but still working like private functions to service.
@Component
public class FunctionServiceHelpers <T> {

    public final FunctionRepo functionRepo;
    public final AiClient<T> functionClient;

    private final String system = "You are making plans in the style of java programming. For example, painting might be the function and prepareToPaint might be one of the sub goals. You are tasked with defining the helper functions.";

    // private final String system = "You are answering a query by identify the main
    // goal of the query as a function. Then identify the functions the main
    // function can be divided into.";

    // private final String system = "You are tasked with making plans by defining
    // goal and sub goals as programming functions.";

    public FunctionServiceHelpers(FunctionRepo functionRepo, AiClient<T> functionClient) {
        this.functionRepo = functionRepo;
        this.functionClient = functionClient;
    }

    public FunctionResponse promptAi(String prompt) throws IOException, InterruptedException {
        return functionClient.promptAi(prompt, system);
    }

    public FunctionEntity saveFunctionResponseAndReturnParent(FunctionResponse response) {
        FunctionEntity parent = toEntity(response.getFunction());

        List<FunctionEntity> children = toEntities(response.getHelperFunctions());

        // 1. Set the parent reference on the children
        children.forEach(child -> child.setParent(parent));

        // 2. Save the parent first
        FunctionEntity finalParent = functionRepo.save(parent);

        // 3. Save the children (this will link them to the saved parent)
        children = functionRepo.saveAll(children);
        List<FunctionEntity> existingHelpers = finalParent.getHelperFunctions();
        existingHelpers.addAll(children);
        finalParent.setHelperFunctions(existingHelpers);
        return finalParent;
    }

    public List<FunctionEntity> saveHelpersAndSetExistingFunction(FunctionEntity parent, FunctionResponse response) {

        FunctionEntity rootFunction = functionRepo.findById(parent.getId()).orElseThrow();

        List<FunctionEntity> children = toEntities(response.getHelperFunctions());

        children.forEach(child -> child.setParent(rootFunction));

        children = functionRepo.saveAll(children);
        List<FunctionEntity> existingHelpers = rootFunction.getHelperFunctions();
        existingHelpers.addAll(children);
        rootFunction.setHelperFunctions(existingHelpers);
        return rootFunction.getHelperFunctions();
    }

    // Expands the tree of linked functions to a the depth of n.
    // It collects all the helper functions of current tree depth responses to then
    // expand on each helper function to the depth of n.
    public List<FunctionEntity> itterateToAResponseLayerOf(List<FunctionEntity> parents, int n)
            throws IOException, InterruptedException {
        List<FunctionEntity> itterating = parents;
        for (int i = 0; i < n; i++) {
            List<FunctionEntity> toItterate = new ArrayList<>();
            for (FunctionEntity function : itterating) {
                List<FunctionEntity> children = expandOnHelperFunction(function);
                toItterate.addAll(children);
            }
            itterating = toItterate;
            i++;
        }
        return itterating;
    }

    
    // Expands each helper function of parameter function.
    // Returns list of helper functions as functions with helper functions.
    public List<FunctionEntity> expandOnHelperFunction(FunctionEntity parent)
            throws IOException, InterruptedException {
        FunctionResponse response = functionClient.promptAi(toDTO(parent).toString(), system);
        List<FunctionEntity> helpers = saveHelpersAndSetExistingFunction(parent, response);
        return helpers;
    }
    // Might make a version of FunctionResponse to shorten tokens in response.

    public FunctionEntity getTree(UUID rootId) {
        List<FunctionEntity> all = functionRepo.fetchSubtree(rootId);

        @SuppressWarnings("null") // As all are fetched from repo they must have Id
        Map<UUID, FunctionEntity> byId = all.stream()
                .collect(Collectors.toMap(FunctionEntity::getId, n -> n));

        FunctionEntity root = byId.get(rootId);

        return root;
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
