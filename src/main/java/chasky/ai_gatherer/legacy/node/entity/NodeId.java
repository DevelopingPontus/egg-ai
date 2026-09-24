// package chasky.ai_gatherer.legacy.node.entity;

// import java.io.Serializable;
// import java.util.Objects;

// import jakarta.persistence.Embeddable;

// @Embeddable
// public class NodeId implements Serializable {
//     private String what;
//     private String how;
//     private String why;

//     // Constructor (required for embeddables)
//     public NodeId() {
//     }
    
//     public NodeId(String what, String how, String why) {
//         this.what = what;
//         this.how = how;
//         this.why = why;
//     }
//     // Getters and setters


//     // equals() and hashCode() are REQUIRED for embeddable IDs
//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (o == null || getClass() != o.getClass())
//             return false;
//         NodeId nodeId = (NodeId) o;
//         return
//                 Objects.equals(what, nodeId.what) &&
//                 Objects.equals(how, nodeId.how) &&
//                         Objects.equals(what, nodeId.why);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash( what, how, why);
//     }

//     public String getWhat() {
//         return what;
//     }

//     public void setWhat(String what) {
//         this.what = what;
//     }

//     public String getHow() {
//         return how;
//     }

//     public void setHow(String how) {
//         this.how = how;
//     }

//     public String getWhy() {
//         return why;
//     }

//     public void setWhy(String why) {
//         this.why = why;
//     }
// }
