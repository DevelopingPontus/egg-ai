package chasky.ai_gatherer.feature.node;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.dto.NodeRequest;
import chasky.ai_gatherer.feature.node.dto.NodeResponse;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {
    
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    public NodeResponse postMethodName(NodeRequest prompt) throws IOException, InterruptedException {
        return nodeService.promptAi(prompt.descriptionOfTopic());
    }

}
