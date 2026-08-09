package chasky.ai_gatherer.feature.node.relation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.dto.TreeNodeDTO;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;

@Service
public class NodeRelationService {
    private final NodeRelationRepository nodeRelationRepository;

    public NodeRelationService(NodeRelationRepository nodeRelationRepository) {
        this.nodeRelationRepository = nodeRelationRepository;
    }

    // public Map<NodeDTO, Map<NodeDTO, ?>> getNodeRelationsTreeOfDepth(int depth,
    // Node node) {
    // Map<NodeDTO, Object> nodeRelations = new LinkedHashMap<>();
    // List<NodeRelation> relations = nodeRelationRepository.findByParentNode(node);

    // for (int i = 0; i < depth; i++) {
    // Set<Node> childNodes = new HashSet<>();
    // for (NodeRelation relation : relations) {
    // Node parentNode = relation.getParentNode();
    // Node childDto = relation.getChildNode();

    // }
    // }
    // }

    public TreeNodeDTO getNodeRelationsTreeOfDepth(int depth, Node node) {
        return buildNodeTree(node, depth, new HashSet<>());
    }

    private TreeNodeDTO buildNodeTree(Node node, int depth, Set<NodeId> visited) {
        if (visited.contains(node.getId()) || depth <= 0) {
            return new TreeNodeDTO(convertToDTO(node), List.of());
        }

        visited.add(node.getId());
        List<TreeNodeDTO> children = new ArrayList<>();

        List<NodeRelation> relations = nodeRelationRepository.findByParentNode(node);
        for (NodeRelation relation : relations) {
            TreeNodeDTO childTree = buildNodeTree(relation.getChildNode(), depth - 1, new HashSet<>(visited));
            children.add(childTree);
        }

        return new TreeNodeDTO(convertToDTO(node), children);
    }

    private NodeDTO convertToDTO(Node node) {
        return new NodeDTO(node.getId()); // Adjust based on your DTO
    }

    public List<NodeRelationDTO> toDtos(List<NodeRelation> relations) {
        List<NodeRelationDTO> relationDtos = new ArrayList<>();
        for (NodeRelation nodeRelation : relations) {
            relationDtos.add(toDto(nodeRelation));
        }
        return relationDtos;
    }

    public NodeRelationDTO toDto(NodeRelation nodeRelation) {
        NodeDTO parentNodeDTO = new NodeDTO(nodeRelation.getParentNode().getId());
        NodeDTO childNodeDTO = new NodeDTO(nodeRelation.getChildNode().getId());
        return new NodeRelationDTO(parentNodeDTO, nodeRelation.getRelationType(),
                childNodeDTO);
    }

}
