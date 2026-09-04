package chasky.ai_gatherer.feature.node.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class NodeId implements Serializable {
    private String category;
    private String topic;

    // Constructor (required for embeddables)
    public NodeId() {
    }

    public NodeId(String category, String topic) {
        this.category = category;
        this.topic = topic;
    }

    // Getters and setters


    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    // equals() and hashCode() are REQUIRED for embeddable IDs
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NodeId nodeId = (NodeId) o;
        return
                Objects.equals(category, nodeId.category) &&
                Objects.equals(topic, nodeId.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash( category, topic);
    }
}
