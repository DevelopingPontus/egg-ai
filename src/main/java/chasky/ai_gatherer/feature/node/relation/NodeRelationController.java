package chasky.ai_gatherer.feature.node.relation;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.NodeService;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/relations")
public class NodeRelationController {
    private final NodeRelationService nodeRelationService;

    public NodeRelationController(NodeRelationService nodeRelationService) {
        this.nodeRelationService = nodeRelationService;
    }

    @GetMapping
    public List<NodeRelationDTO> getNodeRelations(NodeId nodeId) {
        return nodeRelationService.getNodeRelations(5, nodeId);
    }
}
