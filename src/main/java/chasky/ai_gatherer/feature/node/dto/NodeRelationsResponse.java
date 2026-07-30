package chasky.ai_gatherer.feature.node.dto;

import java.util.List;

import chasky.ai_gatherer.feature.node.entity.NodeId;

public record NodeRelationsResponse(
                NodeId nodeId,
                List<NodeRelationDTO> relations,
                Boolean queryWasReasonable) {

}
