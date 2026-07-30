package chasky.ai_gatherer.feature.node;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import chasky.ai_gatherer.feature.node.entity.NodeRelation;

public interface NodeRelationRepository extends JpaRepository<NodeRelation, UUID> {
    
}
