package chasky.ai_gatherer.feature.node;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "category", "topic" })})
public class Node {
    @EmbeddedId
    private NodeId id;

    @OneToMany
    private List<Node> prerequisits;
    // @OneToMany
    // private List<Node> isPrerequisitTo;
    public Node() {
    }
    
    public Node(NodeId id) {
        this.id = id;
    }

    public Node(String category, String topic, List<Node> prerequisits) {
        this.id = new NodeId(category, topic);
        this.prerequisits = prerequisits;
        // this.isPrerequisitTo = isPrerequisitTo;
    }

    public NodeId getId() {
        return id;
    }
    public void setId(NodeId id) {
        this.id = id;
    }
    public List<Node> getTopicsThisDependOn() {
        return new ArrayList<>(prerequisits);
    }
    public void setTopicsThisDependOn(List<Node> prerequisits) {
        this.prerequisits = prerequisits;
    }
    public List<Node> getPrerequisits() {
        return prerequisits;
    }
    public void setPrerequisits(List<Node> prerequisits) {
        this.prerequisits = prerequisits;
    }

    // public List<Node> getTopicsThisEnables() {
    //     return new ArrayList<>(isPrerequisitTo);
    // }
    // public void setTopicsThisEnables(List<Node> isPrerequisitTo) {
    //     this.isPrerequisitTo = isPrerequisitTo;
    // }
}
