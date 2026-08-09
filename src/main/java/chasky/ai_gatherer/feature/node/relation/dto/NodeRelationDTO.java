package chasky.ai_gatherer.feature.node.relation.dto;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.NodeId;

public record NodeRelationDTO(
                NodeDTO parentNodeDto,
                String relationType,
                NodeDTO chilNodeDto
        ) {
}
