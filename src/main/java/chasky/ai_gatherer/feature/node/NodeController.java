package chasky.ai_gatherer.feature.node;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.dto.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.request.NodeRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {
    
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    public NodeRelationsResponse getNodeWithRelations(@Valid NodeRequest prompt) throws IOException, InterruptedException {
        return nodeService.getNodeWithRelations(prompt);
    }

}
