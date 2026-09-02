package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.NodeRelationRepository;
import chasky.ai_gatherer.feature.node.relation.NodeRelationService;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;
import chasky.ai_gatherer.feature.node.relation.output.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.request.NodeRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {
    private final NodeAiClient aiClient;
    private final NodeRepository nodeRepository;
    private final NodeRelationRepository nodeRelationRepository;
    private final NodeRelationService nodeRelationService;

    public NodeService(NodeAiClient aiClient, NodeRepository nodeRepository,
            NodeRelationRepository nodeRelationRepository, NodeRelationService nodeRelationService) {
        this.aiClient = aiClient;
        this.nodeRepository = nodeRepository;
        this.nodeRelationRepository = nodeRelationRepository;
        this.nodeRelationService = nodeRelationService;
    }

    public List<NodeRelationDTO> generateNodeWithRelations(NodeRequest prompt)
            throws IOException, InterruptedException {
        NodeRelationsResponse response = aiClient.promptAi(prompt.toString());
        if (response.queryWasReasonable() == false) {
            System.out.println("Query was not reasonable.");
        }

        saveNodesIfMissing(response);
        saveNodeRelations(response);

        List<NodeRelationDTO> relatedNodes = nodeRelationService.getNodeRelations(5,
                response.treeOfNodes().getFirst().nodeDTO().nodeId());
        return relatedNodes;
    }

    public List<NodeDTO> getAllNodes() {
        List<Node> nodes = nodeRepository.findAll();
        return nodes.stream().map(node -> toDto(node)).toList();
    }

    private void saveNodesIfMissing(NodeRelationsResponse response) {
        List<Node> nodes = new ArrayList<>();

        for (NodeRelationDTO relation : response.treeOfNodes()) {
            if (nodeRepository.findById(relation.relatedNodeDTO().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.relatedNodeDTO().nodeId()));
            }
            if (nodeRepository.findById(relation.nodeDTO().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.nodeDTO().nodeId()));
            }
        }
        nodeRepository.saveAll(nodes);
    }

    private void saveNodeRelations(NodeRelationsResponse response) {
        for (NodeRelationDTO relation : response.treeOfNodes()) {
            Node node = nodeRepository.findById(relation.nodeDTO().nodeId()).get();
            Node relatedNode = nodeRepository.findById(relation.relatedNodeDTO().nodeId()).get();
            NodeRelation nodeRelation = new NodeRelation(node, relation.relationType(), relatedNode);
            try {
                nodeRelationRepository.save(nodeRelation);
            } catch (Exception e) {
                System.out.println("Error saving node relation");
            }
        }
    }

    public List<NodeDTO> toDtos(List<Node> nodes) {
        List<NodeDTO> nodeDtos = new ArrayList<>();
        for (Node node : nodes) {
            nodeDtos.add(toDto(node));
        }
        return nodeDtos;
    }

    public NodeDTO toDto(Node node) {
        return new NodeDTO(node.getId());
    }
}
