package chasky.ai_gatherer.feature.node;

import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "category", "topic" })})
public class Node {
    @EmbeddedId
    private NodeId id;

    private List<String> topicsThisDependOn;
    private List<String> topicsThisEnables;
    public Node() {
    }
    public Node(String category, String topic, List<String> topicsThisDependOn, List<String> topicsThisEnables) {
        this.id = new NodeId(category, topic);
        this.topicsThisDependOn = topicsThisDependOn;
        this.topicsThisEnables = topicsThisEnables;
    }
    public NodeId getId() {
        return id;
    }
    public List<String> getTopicsThisDependOn() {
        return topicsThisDependOn;
    }
    public void setTopicsThisDependOn(List<String> topicsThisDependOn) {
        this.topicsThisDependOn = topicsThisDependOn;
    }
    public List<String> getTopicsThisEnables() {
        return topicsThisEnables;
    }
    public void setTopicsThisEnables(List<String> topicsThisEnables) {
        this.topicsThisEnables = topicsThisEnables;
    }
}
