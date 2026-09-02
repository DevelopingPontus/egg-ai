package chasky.ai_gatherer.feature.node.relation.output;

import java.util.List;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;

public record NodeRelationsResponse(
                String reasoning,
        List<NodeRelationDTO> treeOfNodes,
        Boolean queryWasReasonable) {

}
