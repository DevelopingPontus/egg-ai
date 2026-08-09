package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.NodeRelationRepository;
import chasky.ai_gatherer.feature.node.relation.NodeRelationService;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.dto.TreeNodeDTO;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;
import chasky.ai_gatherer.feature.node.relation.output.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.request.NodeRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public NodeRelationsResponse generateNodeWithRelations(NodeRequest prompt)
            throws IOException, InterruptedException {
        NodeRelationsResponse response = aiClient.promptAi(prompt.toString());
        if (response.queryWasReasonable() == false) {
            return response;
        }

        saveNodesIfMissing(response);
        saveNodeRelations(response);

        return response;
    }

    public List<NodeDTO> getAllNodes() {
        List<Node> nodes = nodeRepository.findAll();
        return nodes.stream().map(node -> toDto(node)).toList();
    }



    public TreeNodeDTO getNodeRelationsTreeOfDepth(@Size(min = 1, max = 10) int depth, NodeId nodeId) {
        if (nodeRepository.findById(nodeId).isEmpty()) {
            throw new EntityNotFoundException("Could not find Node by NodeId");
        }
        Node node = nodeRepository.findById(nodeId).get();
        return nodeRelationService.getNodeRelationsTreeOfDepth(depth, node);
    }

  
    private void saveNodesIfMissing(NodeRelationsResponse response) {
        List<Node> nodes = new ArrayList<>();

        for (NodeRelationDTO relation : response.relatedNodes()) {
            if (nodeRepository.findById(relation.parentNodeDto().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.parentNodeDto().nodeId()));
            }
            if (nodeRepository.findById(relation.chilNodeDto().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.chilNodeDto().nodeId()));
            }
        }
        nodeRepository.saveAll(nodes);
    }

    private void saveNodeRelations(NodeRelationsResponse response) {
        for (NodeRelationDTO relation : response.relatedNodes()) {
            Node parentNode = nodeRepository.findById(relation.parentNodeDto().nodeId()).get();
            Node childNode = nodeRepository.findById(relation.chilNodeDto().nodeId()).get();
            NodeRelation nodeRelation = new NodeRelation(parentNode, relation.relationType(), childNode);
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
