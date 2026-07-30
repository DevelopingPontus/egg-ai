package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.dto.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeRelation;
import chasky.ai_gatherer.feature.node.request.NodeRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {
    private final NodeAiClient aiClient;
    private final NodeRepository nodeRepository;
    private final NodeRelationRepository nodeRelationRepository;

    public NodeService(NodeAiClient aiClient, NodeRepository nodeRepository,
            NodeRelationRepository nodeRelationRepository) {
        this.aiClient = aiClient;
        this.nodeRepository = nodeRepository;
        this.nodeRelationRepository = nodeRelationRepository;
    }

    public NodeRelationsResponse getNodeWithRelations(NodeRequest prompt)
            throws IOException, InterruptedException {
        NodeRelationsResponse response = aiClient.promptAi(prompt.toString());
        if (response.queryWasReasonable() == false) {
            return response;
        }

        saveNodesIfMissing(response);
        saveNodeRelations(response);

        return response;
    }

    private void saveNodesIfMissing(NodeRelationsResponse response) {
        List<Node> nodes = new ArrayList<>();
        if (nodeRepository.findById(response.nodeId()).isEmpty()) {
            nodes.add(new Node(response.nodeId()));
        }

        for (NodeRelationDTO relation : response.relations()) {
            if (nodeRepository.findById(relation.relatedNodeId()).isEmpty()) {
                nodes.add(new Node(relation.relatedNodeId()));
            }
        }

        nodeRepository.saveAll(nodes);
    }

    private void saveNodeRelations(NodeRelationsResponse response) {
        List<NodeRelation> relations = new ArrayList<>();
        Node sourceNode = nodeRepository.findById(response.nodeId()).get();
        for (NodeRelationDTO relation : response.relations()) {
            Node relatedNode = nodeRepository.findById(relation.relatedNodeId()).get();
            relations.add(new NodeRelation(sourceNode, relatedNode, relation.relationType()));
        }
        nodeRelationRepository.saveAll(relations);
    }


}
