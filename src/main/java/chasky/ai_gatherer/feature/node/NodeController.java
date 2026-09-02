package chasky.ai_gatherer.feature.node;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.output.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.request.NodeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {
    
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    public List<NodeRelationDTO> generateNodeWithRelations(@Valid NodeRequest prompt)
            throws IOException, InterruptedException {
        return nodeService.generateNodeWithRelations(prompt);
    }
    
    @GetMapping
    public List<NodeDTO> getAllNodes()
            throws IOException, InterruptedException {
        return nodeService.getAllNodes();
    }


}
