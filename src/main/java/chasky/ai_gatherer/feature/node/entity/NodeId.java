package chasky.ai_gatherer.feature.node.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class NodeId implements Serializable {
    private String dataType;
    private String category;
    private String topic;

    // Constructor (required for embeddables)
    public NodeId() {
    }

    public NodeId(String dataType, String category, String topic) {
        this.dataType = dataType;
        this.category = category;
        this.topic = topic;
    }

    // Getters and setters

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
        return Objects.equals(dataType, nodeId.dataType) &&
                Objects.equals(category, nodeId.category) &&
                Objects.equals(topic, nodeId.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataType, category, topic);
    }
}
