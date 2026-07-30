package chasky.ai_gatherer.feature.node.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class NodeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Node sourceNode;

    @ManyToOne
    private Node relatedNode;

    private String relationType;

    public NodeRelation() {
    }

    public NodeRelation(Node sourceNode, Node relatedNode, String relationType) {
        this.sourceNode = sourceNode;
        this.relatedNode = relatedNode;
        this.relationType = relationType;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Node getRelatedNode() {
        return relatedNode;
    }

    public void setRelatedNode(Node relatedNode) {
        this.relatedNode = relatedNode;
    }

    
}