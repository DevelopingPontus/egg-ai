package chasky.ai_gatherer.feature.node.relation.dto;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;

public record NodeRelationDTO(
                NodeDTO parentDTO,
                String relationType,
                NodeDTO childDTO
        ) {
}
