// package chasky.ai_gatherer.legacy.node.entity;

// import java.util.ArrayList;
// import java.util.List;

// import chasky.ai_gatherer.legacy.node.relation.entity.NodeRelation;
// import jakarta.persistence.CascadeType;
// import jakarta.persistence.EmbeddedId;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;
// import net.bytebuddy.agent.builder.AgentBuilder.PoolStrategy.Eager;

// @Entity
// public class Node {
//     @EmbeddedId
//     private NodeId id;

//     @OneToMany
//     private List<NodeRelation> relations;

//     public Node() {
//         this.relations = new ArrayList<>();
//     }

//     public Node(NodeId id) {
//         this.id = id;
//         this.relations = new ArrayList<>();
//     }

//     public Node(NodeId id, List<NodeRelation> relations) {
//         this.id = id;
//         this.relations = relations;
//     }

//     public NodeId getId() {
//         return id;
//     }

//     public void setId(NodeId id) {
//         this.id = id;
//     }

//     public List<NodeRelation> getrelations() {
//         return relations;
//     }

//     public void setrelations(List<NodeRelation> relations) {
//         this.relations = relations;
//     }

//     public void removeRelation(Node relation) {
//         this.relations.remove(relation);
//     }

//     public void removerelations(List<NodeRelation> relations) {
//         this.relations.removeAll(relations);
//     }

//     public void addRelation(NodeRelation relation) {
//     this.relations.add(relation);
//     }

//     public void addrelations(List<NodeRelation> relations) {
//     this.relations.addAll(relations);
//     }

// }

