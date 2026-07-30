package chasky.ai_gatherer.feature.node.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Node {
    @EmbeddedId
    private NodeId id;

    @OneToMany(mappedBy = "sourceNode", cascade = CascadeType.ALL)
    private List<NodeRelation> relations;

    public Node() {
        this.relations = new ArrayList<>();
    }

    public Node(NodeId id) {
        this.id = id;
        this.relations = new ArrayList<>();
    }

    public Node(NodeId id, List<NodeRelation> relations) {
        this.id = id;
        this.relations = relations;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public List<NodeRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<NodeRelation> relations) {
        this.relations = relations;
    }

    public void removeRelation(Node relation) {
        this.relations.remove(relation);
    }

    public void removeRelations(List<NodeRelation> relations) {
        this.relations.removeAll(relations);
    }

    public void addRelation(NodeRelation relation) {
    this.relations.add(relation);
    }

    public void addRelations(List<NodeRelation> relations) {
    this.relations.addAll(relations);
    }

}

