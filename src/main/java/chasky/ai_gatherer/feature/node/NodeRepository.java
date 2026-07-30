package chasky.ai_gatherer.feature.node;

import org.springframework.data.jpa.repository.JpaRepository;

import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;

public interface NodeRepository extends JpaRepository<Node, NodeId> {
    
}
