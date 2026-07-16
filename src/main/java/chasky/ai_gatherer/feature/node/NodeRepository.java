package chasky.ai_gatherer.feature.node;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, NodeId> {
    
}
