package chasky.ai_gatherer.feature.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import chasky.ai_gatherer.feature.function.dto.FunctionDTO;
import chasky.ai_gatherer.feature.function.dto.FunctionResponse;
import chasky.ai_gatherer.feature.function.entity.FunctionEntity;

@SpringBootTest
@Transactional
public class FunctionServiceHelpersTest {

    @Autowired
    private FunctionServiceHelpers helpers;

    @Autowired
    private FunctionRepo functionRepo;

    @BeforeEach
    private void setUp() {
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
        response.setReasoning("RootBecause");
        return response;
    }

    @Test
    void testFunctionResponseGeneration() {
        FunctionResponse response = functionResponse(3);
        assertTrue(response.getHelperFunctions() != null);
        assertEquals(response.getHelperFunctions().size(), 3);
    }

    @Test
    void testSaveFunctionResponseAndReturnParent() {
        FunctionEntity entity = helpers.saveFunctionResponseAndReturnParent(functionResponse(3));

        List<FunctionEntity> helperFunctions = functionRepo.findAllByParent(entity);
        assertNotNull(helperFunctions);
        assertEquals(helperFunctions.size(), 3);
    }
}
