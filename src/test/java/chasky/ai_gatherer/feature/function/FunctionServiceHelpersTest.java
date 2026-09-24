package chasky.ai_gatherer.feature.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import chasky.ai_gatherer.feature.function.dto.FunctionDTO;
import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;
import chasky.ai_gatherer.feature.function.util.FunctionAiClient;

@SpringBootTest
@Transactional
public class FunctionServiceHelpersTest {

    @MockitoBean
    private FunctionAiClient functionClient;

    @Autowired
    private FunctionServiceHelpers helpers;

    @Autowired
    private FunctionRepo functionRepo;

    private int numberOfMockHelpers = 3;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        when(functionClient.promptAi(any(), any()))
                .thenReturn(functionResponse(numberOfMockHelpers));
    }

    private FunctionDTO functionDTO() {
        FunctionDTO function = new FunctionDTO();
        function.setName("Random");
        function.setReasoning("Because");
        return function;
    }

    private FunctionResponse functionResponse(int n) {
        FunctionResponse response = new FunctionResponse();
        response.setFunction(functionDTO());
        List<FunctionDTO> helperDTOs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            helperDTOs.add(functionDTO());
        }
        response.setHelperFunctions(helperDTOs);
        // response.setReasoning("RootBecause");
        return response;
    }

    private FunctionEntity functionEntity() {
        FunctionEntity entity = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        return entity;
    }

    @Test
    void validateMockOfPromtAiWorks() throws IOException, InterruptedException {
        FunctionResponse response = functionClient.promptAi("null", "null");
        assertNotNull(response);
        assertEquals(response.getHelperFunctions().size(), numberOfMockHelpers);
    }

    @Test
    void testFunctionResponseGeneration() {
        FunctionResponse response = functionResponse(numberOfMockHelpers);
        assertNotNull(response.getHelperFunctions());
        assertEquals(response.getHelperFunctions().size(), numberOfMockHelpers);
    }

    @Test
    void testSaveFunctionResponseAndReturnParent() {
        FunctionEntity entity = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));

        assertNotNull(entity.getHelperFunctions());
        assertNotNull(entity.getHelperFunctions().getFirst().getId());
        assertEquals(entity.getHelperFunctions().size(), numberOfMockHelpers);

        List<FunctionEntity> helperFunctions = functionRepo.findAllByParent(entity);
        assertNotNull(helperFunctions);
        assertEquals(helperFunctions.size(), numberOfMockHelpers);
        assertEquals(entity.getHelperFunctions().getFirst().getId(), helperFunctions.getFirst().getId());
    }

    @Test
    void testSaveHelpersAndSetExistingFunction() {
        FunctionEntity root = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));

        FunctionResponse response = functionResponse(numberOfMockHelpers);

        helpers.saveHelpersAndSetExistingFunction(root, response);
        List<FunctionEntity> helpersToRoot = functionRepo.findAllByParent(root);
        assertNotNull(root.getHelperFunctions());
        assertNotNull(helpersToRoot);
        // Both helpers generated with root and added helpers should be pressent in
        // root.
        assertEquals(root.getHelperFunctions().size(), 2 * numberOfMockHelpers);
    }

    @Test
    void testItterateToAResponseLayerOf() {
        FunctionEntity root = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        List<FunctionEntity> helperFunctions = root.getHelperFunctions();
        assertEquals(helperFunctions.size(), 3);
        try {
            helpers.itterateToAResponseLayerOf(helperFunctions, 3);
        } catch (Exception e) {
            // TODO: handle exception
        }

        FunctionEntity savedEntity = functionRepo.findById(root.getId()).orElse(null);
        assertEquals(savedEntity, root);
        FunctionEntity helper1 = functionRepo.findById(root.getHelperFunctions().getFirst().getId()).get();
        FunctionEntity helper2 = functionRepo.findById(helper1.getHelperFunctions().getFirst().getId()).get();
        FunctionEntity helper3 = functionRepo.findById(helper2.getHelperFunctions().getFirst().getId()).get();
        assertNotNull(helper1);
        assertNotNull(helper2);
        assertNotNull(helper3);
        assertNotEquals(helper2.getParent(), helper3.getParent());
        assertEquals(helper2.getParent(), helper1);
        assertEquals(helper3.getParent(), helper2);

    }

    @Test
    void testExpandOnHelperFunction() throws IOException, InterruptedException {
        FunctionEntity parent = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        assertEquals(parent.getHelperFunctions().size(), numberOfMockHelpers);
        List<FunctionEntity> children = helpers.expandOnHelperFunction(parent);
        assertNotNull(children);
        assertEquals(children.size(), 2 * numberOfMockHelpers);
    }

    @Test
    void testConstructTree() throws IOException, InterruptedException {
        FunctionEntity parent = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        helpers.itterateToAResponseLayerOf(parent.getHelperFunctions(), 2);
        parent = helpers.getTree(parent.getId());
        assertNotNull(parent.getHelperFunctions().getFirst().getHelperFunctions().getFirst());
        assertEquals(parent.getHelperFunctions().size(), 3);
        assertEquals(parent.getHelperFunctions().getFirst().getHelperFunctions().size(), 3);
    }

    @Test
    void testFetchSubTree() throws IOException, InterruptedException {
        FunctionEntity parent = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        helpers.itterateToAResponseLayerOf(parent.getHelperFunctions(), 1);
        List<FunctionEntity> subTree = helpers.functionRepo.fetchSubtree(parent.getId());
        assertEquals(subTree.size(), 13);
        assertNotNull(subTree);
    }

    @Test 
    void testFetchRoot() throws IOException, InterruptedException {
        FunctionEntity parent = helpers.saveFunctionResponseAndReturnParent(functionResponse(numberOfMockHelpers));
        List<FunctionEntity> leafs = helpers.itterateToAResponseLayerOf(parent.getHelperFunctions(), 1);
        FunctionEntity rootToLeaf = functionRepo.fetchRoot(leafs.getFirst().getId());

        assertEquals(parent.getId(), rootToLeaf.getId());
    }
}
