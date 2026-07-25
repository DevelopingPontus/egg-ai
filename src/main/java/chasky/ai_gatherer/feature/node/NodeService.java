package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        Node mainNode = new Node(new NodeId(response.subject(), response.topic()));
        List<Node> prerequisitNodes = toNodes(response.prerequisits());
        List<Node> isPrerequisitToNodes = toNodes(response.isPrerequisitTo());

        List<Node> updatedNodes = updateNodes(mainNode, prerequisitNodes, isPrerequisitToNodes);

        nodeRepository.saveAll(updatedNodes);

        return response;
    }

    private List<Node> toNodes(List<NodeId> response) {
        List<Node> nodes = new ArrayList<>();
        for (NodeId nodeId : response) {
            nodes.add(new Node(nodeId));
        }
        return nodes;
    }

    private List<Node> updateNodes(Node mainNode, List<Node> prerequisitNodes, List<Node> isPrerequisitToNodes) {
        List<Node> updatedNodes = new ArrayList<>();
        Set<Node> mainPrerequisits = mainNode.getPrerequisits();
        Set<Node> mainIsPrerequisitTo = mainNode.getIsPrerequisitTo();

        if (nodeRepository.findById(mainNode.getId()).isPresent()) {
            Node existingMainNode = nodeRepository.findById(mainNode.getId()).get();

            mainPrerequisits.addAll(existingMainNode.getPrerequisits());
            mainNode.setPrerequisits(mainPrerequisits);

            mainIsPrerequisitTo.addAll(existingMainNode.getIsPrerequisitTo());
            mainNode.setIsPrerequisitTo(mainIsPrerequisitTo);
        }

        for (Node node : prerequisitNodes) {
            mainPrerequisits.add(node);

            if (nodeRepository.findById(node.getId()).isPresent()) {
                Node existingNode = nodeRepository.findById(node.getId()).get();

                Set<Node> prerequisits = node.getPrerequisits();
                prerequisits.addAll(existingNode.getPrerequisits());
                node.setPrerequisits(prerequisits);

                Set<Node> isPrerequisitTo = node.getIsPrerequisitTo();
                isPrerequisitTo.addAll(existingNode.getIsPrerequisitTo());
                node.setIsPrerequisitTo(isPrerequisitTo);
            }
            updatedNodes.add(node);
        }

        for (Node node : isPrerequisitToNodes) {
            mainIsPrerequisitTo.add(node);

            if (nodeRepository.findById(node.getId()).isPresent()) {
                Node existingNode = nodeRepository.findById(node.getId()).get();

                Set<Node> prerequisits = node.getPrerequisits();
                prerequisits.addAll(existingNode.getPrerequisits());
                node.setPrerequisits(prerequisits);

                Set<Node> isPrerequisitTo = node.getIsPrerequisitTo();
                isPrerequisitTo.addAll(existingNode.getIsPrerequisitTo());
                node.setIsPrerequisitTo(isPrerequisitTo);
            }
            updatedNodes.add(node);
        }
        updatedNodes.add(mainNode);
        return updatedNodes;
    }

}
