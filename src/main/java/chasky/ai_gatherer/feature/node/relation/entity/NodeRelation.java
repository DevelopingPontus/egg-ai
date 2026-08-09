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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"parent_node_topic", "child_node_topic", "parent_node_category", "child_node_category"}))
public class NodeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    private Node parentNode;

    @ManyToOne
    private Node childNode;

    private String relationType;
 
    public NodeRelation() {
    }

    public NodeRelation(Node parentNode, String relationType, Node childNode) {
        this.parentNode = parentNode;
        this.childNode = childNode;
        this.relationType = relationType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getChildNode() {
        return childNode;
    }

    public void setChildNode(Node childNode) {
        this.childNode = childNode;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }


    
}