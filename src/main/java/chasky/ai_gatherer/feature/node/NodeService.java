package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeResponse;

import java.io.IOException;
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
            nodeRepository.save(toNode(response));
        }
        return response;
    }

    private Node toNode(NodeResponse response) {
        return new Node(response.subject(),
                response.topic(),
                response.topicsThisDependOn(),
                response.topicsThisEnables());
    }
}
