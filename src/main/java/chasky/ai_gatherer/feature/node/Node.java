package chasky.ai_gatherer.feature.node;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Node {
    @EmbeddedId
    private NodeId id;

    @OneToMany
    private Set<Node> prerequisits;
    @OneToMany
    private Set<Node> isPrerequisitTo;

    public Node() {
        this.prerequisits = new HashSet<>();
        this.isPrerequisitTo = new HashSet<>();
    }

    public Node(NodeId id) {
        this.id = id;
        this.prerequisits = new HashSet<>();
        this.isPrerequisitTo = new HashSet<>();
    }

    public Node(String category, String topic, Set<Node> prerequisits, Set<Node> isPrerequisitTo) {
        this.id = new NodeId(category, topic);
        this.prerequisits = prerequisits;
        this.isPrerequisitTo = isPrerequisitTo;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public Set<Node> getPrerequisits() {
        return prerequisits;
    }

    public void setPrerequisits(Set<Node> prerequisits) {
        this.prerequisits = prerequisits;
    }

    public Set<Node> getIsPrerequisitTo() {
        return isPrerequisitTo;
    }

    public void setIsPrerequisitTo(Set<Node> isPrerequisitTo) {
        this.isPrerequisitTo = isPrerequisitTo;
    }

}


// package chasky.ai_gatherer.feature.node;

// import java.util.HashSet;
// import java.util.Set;

// import jakarta.persistence.EmbeddedId;
// import jakarta.persistence.Entity;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;

// @Entity
// @Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "category", "topic" })})
// public class Node {
//     @EmbeddedId
//     private NodeId id;

//     @OneToMany
//     private Set<Node> prerequisits;
//     @OneToMany
//     private Set<Node> isPrerequisitTo;

//     public Node() {
//         this.prerequisits = new HashSet<>();
//         this.isPrerequisitTo = new HashSet<>();
//     }

//     public Node(NodeId id) {
//         this.id = id;
//         this.prerequisits = new HashSet<>();
//         this.isPrerequisitTo = new HashSet<>();
//     }

//     public Node(String category, String topic, Set<Node> prerequisits, Set<Node> isPrerequisitTo) {
//         this.id = new NodeId(category, topic);
//         this.prerequisits = prerequisits;
//         this.isPrerequisitTo = isPrerequisitTo;
//     }

//     public NodeId getId() {
//         return id;
//     }
//     public void setId(NodeId id) {
//         this.id = id;
//     }
//     public Set<Node> getPrerequisits() {
//         return prerequisits;
//     }
//     public void setPrerequisits(Set<Node> prerequisits) {
//         this.prerequisits = prerequisits;
//     }

//     public Set<Node> getIsPrerequisitTo() {
//         return isPrerequisitTo;
//     }

//     public void setIsPrerequisitTo(Set<Node> isPrerequisitTo) {
//         this.isPrerequisitTo = isPrerequisitTo;
//     }

// }
