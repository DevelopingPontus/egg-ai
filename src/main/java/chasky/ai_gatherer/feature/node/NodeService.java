package chasky.ai_gatherer.feature.node;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.NodeRelationRepository;
import chasky.ai_gatherer.feature.node.relation.NodeRelationService;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;
import chasky.ai_gatherer.feature.node.relation.output.NodeRelationsResponse;
import chasky.ai_gatherer.feature.node.request.NodeRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {
    private final NodeAiClient aiClient;
    private final NodeRepository nodeRepository;
    private final NodeRelationRepository nodeRelationRepository;
    private final NodeRelationService nodeRelationService;

    ObjectMapper mapper = new ObjectMapper();

    public NodeService(NodeAiClient aiClient, NodeRepository nodeRepository,
            NodeRelationRepository nodeRelationRepository, NodeRelationService nodeRelationService) {
        this.aiClient = aiClient;
        this.nodeRepository = nodeRepository;
        this.nodeRelationRepository = nodeRelationRepository;
        this.nodeRelationService = nodeRelationService;
    }

    public List<NodeRelationDTO> generateNodeWithRelations(NodeRequest prompt)
            throws IOException, InterruptedException {
        NodeRelationsResponse response = aiClient.promptAi(prompt.toString(),
                "Define the core node and it's closest relations.");
        if (response.queryWasReasonable() == false) {
            System.out.println("Query was not reasonable.");
        }
        // else {
        //     for (int i = 0; i < 2; i++) {
        //         response = itterateAnswer("", response);
        //     }
        // }

        saveNodesIfMissing(response);
        saveNodeRelations(response);

        return response.treeOfNodes();
    }
    
    public List<NodeRelationDTO> expandNodeWithRelations(NodeDTO node)
            throws IOException, InterruptedException {
        NodeRelationsResponse response = aiClient.promptAi(node.toString(),
                "Define the closest relations to this node.");
        if (response.queryWasReasonable() == false) {
            System.out.println("Query was not reasonable.");
        }

        NodeRelationsResponse finalResponse = setOriginalNodeToRelations(response, node);

        // else {
        // for (int i = 0; i < 2; i++) {
        // response = itterateAnswer("", response);
        // }
        // }

        saveNodesIfMissing(finalResponse);
        saveNodeRelations(finalResponse);

        return finalResponse.treeOfNodes();
    }
    
    private NodeRelationsResponse setOriginalNodeToRelations(NodeRelationsResponse response, NodeDTO rootNode) {
        List<NodeRelationDTO> relations = response.treeOfNodes();
        List<NodeRelationDTO> finalRelations = new ArrayList<>();

        for (NodeRelationDTO relation : relations) {
            finalRelations.add(new NodeRelationDTO(rootNode, relation.relationType(), relation.childDTO()));
        }
        
        return new NodeRelationsResponse(response.reasoning(), finalRelations, response.queryWasReasonable());
    }

    private NodeRelationsResponse itterateAnswer(String extraInput, NodeRelationsResponse response)
            throws IOException, InterruptedException {

        String jsonReasoing = response.reasoning();

        jsonReasoing = jsonReasoing
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");

        NodeRelationsResponse itterableResponse = new NodeRelationsResponse(
                jsonReasoing,
                response.treeOfNodes(),
                response.queryWasReasonable());

        response = aiClient.promptAi(extraInput + itterableResponse, "");

        return response;
    }

    public List<NodeDTO> getAllNodes() {
        List<Node> nodes = nodeRepository.findAll();
        return nodes.stream().map(node -> toDto(node)).toList();
    }

    private void saveNodesIfMissing(NodeRelationsResponse response) {
        List<Node> nodes = new ArrayList<>();

        for (NodeRelationDTO relation : response.treeOfNodes()) {
            if (nodeRepository.findById(relation.childDTO().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.childDTO().nodeId()));
            }
            if (nodeRepository.findById(relation.parentDTO().nodeId()).isEmpty()) {
                nodes.add(new Node(relation.parentDTO().nodeId()));
            }
        }
        nodeRepository.saveAll(nodes);
    }

    private void saveNodeRelations(NodeRelationsResponse response) {
        for (NodeRelationDTO relation : response.treeOfNodes()) {
            Node parent = nodeRepository.findById(relation.parentDTO().nodeId()).get();
            Node child = nodeRepository.findById(relation.childDTO().nodeId()).get();
            NodeRelation nodeRelation = new NodeRelation(parent, relation.relationType(), child);
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
