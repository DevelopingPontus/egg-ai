package chasky.ai_gatherer.feature.node.dto;


import java.util.Set;

import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;

public record NodeDTO(
        NodeId nodeId,
        Set<Node> relations
) {

}
