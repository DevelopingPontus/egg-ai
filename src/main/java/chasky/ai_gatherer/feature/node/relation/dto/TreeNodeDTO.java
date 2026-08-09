package chasky.ai_gatherer.feature.node.relation.dto;

import java.util.List;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;

public record TreeNodeDTO(
        NodeDTO node,
        List<TreeNodeDTO> children) {

}
