package chasky.ai_gatherer.feature.node.relation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;

public interface NodeRelationRepository extends JpaRepository<NodeRelation, UUID> {
   List<NodeRelation> findByParent(Node parentNode);

   List<NodeRelation> findByChild(Node childNode);

}
