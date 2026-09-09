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
    private Node parent;

    private String relationType;

    @ManyToOne
    private Node child;


    public NodeRelation() {
    }

    public NodeRelation(Node parent, String relationType, Node child) {
        this.parent = parent;
        this.child = child;
        this.relationType = relationType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

}