package chasky.ai_gatherer.feature.node.relation.entity;

import java.util.UUID;

import chasky.ai_gatherer.feature.node.entity.Node;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "node_topic", "related_node_topic", "node_category",
        "related_node_category" }))
public class NodeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    private Node node;

    @ManyToOne
    private Node relatedNode;

    private String relationType;

    public NodeRelation() {
    }

    public NodeRelation(Node node, String relationType, Node relatedNode) {
        this.node = node;
        this.relatedNode = relatedNode;
        this.relationType = relationType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Node getnode() {
        return node;
    }

    public void setnode(Node node) {
        this.node = node;
    }

    public Node getrelatedNode() {
        return relatedNode;
    }

    public void setrelatedNode(Node relatedNode) {
        this.relatedNode = relatedNode;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

}