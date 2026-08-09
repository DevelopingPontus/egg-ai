package chasky.ai_gatherer.feature.node.relation.output;

import java.util.List;

import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;

public record NodeRelationsResponse(
        String query,
        String context,
        List<NodeRelationDTO> relatedNodes,
        Boolean queryWasReasonable) {

}
