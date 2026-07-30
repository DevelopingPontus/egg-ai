package chasky.ai_gatherer.feature.node.dto;

import chasky.ai_gatherer.feature.node.entity.NodeId;

public record NodeRelationDTO(
        String relationType,
        NodeId relatedNodeId) {
}
