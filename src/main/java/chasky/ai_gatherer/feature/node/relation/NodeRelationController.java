package chasky.ai_gatherer.feature.node.relation;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.NodeService;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.TreeNodeDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/relations")
public class NodeRelationController {
    private final NodeService nodeService;

    public NodeRelationController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping
    public TreeNodeDTO getNodeRelationsTreeOfDepth(@Valid NodeId nodeId) {
        return nodeService.getNodeRelationsTreeOfDepth(10, nodeId);
    }
}
