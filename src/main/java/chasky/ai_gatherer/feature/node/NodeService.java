package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NodeService {
    private final NodeAiClient aiClient;
    private final NodeRepository nodeRepository;

    public NodeService(NodeAiClient aiClient, NodeRepository nodeRepository) {
        this.aiClient = aiClient;
        this.nodeRepository = nodeRepository;
    }

    public NodeResponse promptAi(String prompt) throws IOException, InterruptedException {
        NodeResponse response = aiClient.promptAi(prompt);
        Optional<Node> node = nodeRepository.findById(new NodeId(response.subject(), response.topic()));
        if (node.isEmpty() || node == null) {
            nodeRepository.saveAll(toNode(response));
        }
        return response;
    }

    private List<Node> toNode(NodeResponse response) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(new NodeId(response.subject(), response.topic())));
        for (NodeId nodeId : response.prerequisits()) {
            nodes.add(new Node(nodeId));
        }
        return nodes;
    }
}
