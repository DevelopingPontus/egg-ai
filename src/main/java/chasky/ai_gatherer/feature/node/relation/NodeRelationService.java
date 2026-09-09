package chasky.ai_gatherer.feature.node.relation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.feature.node.NodeRepository;
import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.Node;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.relation.entity.NodeRelation;
import jakarta.persistence.EntityNotFoundException;

@Service
public class NodeRelationService {
    private final NodeRelationRepository nodeRelationRepository;
    private final NodeRepository nodeRepository;

    public NodeRelationService(NodeRelationRepository nodeRelationRepository, NodeRepository nodeRepository) {
        this.nodeRelationRepository = nodeRelationRepository;
        this.nodeRepository = nodeRepository;
    }

    public List<NodeRelationDTO> getNodeRelations(int depth, NodeId nodeId) {
        if (nodeRepository.findById(nodeId).isEmpty()) {
            throw new EntityNotFoundException("Could not find Node by NodeId");
        }
        Node node = nodeRepository.findById(nodeId).get();
        Set<NodeId> visited = new HashSet<>();
        Set<NodeRelation> allRelations = new HashSet<>();

        // collectRelations(node, depth, visited, allRelations);
        collectRelations(node, visited, allRelations);
        System.out.println("All linked relations collected by node in tree " + allRelations.size());

        return allRelations.stream()
                .map(this::toDto)
                .toList();
    }

    // private void collectRelations(Node node, int depth, Set<NodeId> visited,
    // Set<NodeRelation> allRelations) {
    // if (depth <= 0 || visited.contains(node.getId())) {
    // return;
    // }

    // visited.add(node.getId());

    // // Get direct relations
    // List<NodeRelation> parentRelations = nodeRelationRepository.findByNode(node);
    // List<NodeRelation> childRelations =
    // nodeRelationRepository.findByRelatedNode(node);

    // allRelations.addAll(childRelations);
    // allRelations.addAll(parentRelations);

    // // Recursively fetch next level
    // for (NodeRelation relation : childRelations) {
    // collectRelations(relation.getnode(), depth - 1, visited, allRelations);
    // }
    // for (NodeRelation relation : parentRelations) {
    // collectRelations(relation.getrelatedNode(), depth - 1, visited,
    // allRelations);
    // }
    // }
    private void collectRelations(
            Node start,
            Set<NodeId> visited,
            Set<NodeRelation> allRelations) {

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (!visited.add(node.getId())) {
                continue;
            }

            List<NodeRelation> outgoing = nodeRelationRepository.findByParent(node);

            List<NodeRelation> incoming = nodeRelationRepository.findByChild(node);

            for (NodeRelation relation : outgoing) {
                if (allRelations.add(relation)) {
                    queue.add(relation.getChild());
                }
            }

            for (NodeRelation relation : incoming) {
                if (allRelations.add(relation)) {
                    queue.add(relation.getParent());
                }
            }
        }
    }

    public List<NodeRelationDTO> toDtos(List<NodeRelation> relations) {
        List<NodeRelationDTO> relationDtos = new ArrayList<>();
        for (NodeRelation nodeRelation : relations) {
            relationDtos.add(toDto(nodeRelation));
        }
        return relationDtos;
    }

    public NodeRelationDTO toDto(NodeRelation nodeRelation) {
        NodeDTO nodeDto = new NodeDTO(nodeRelation.getParent().getId());
        NodeDTO relatedNodeDTO = new NodeDTO(nodeRelation.getChild().getId());
        return new NodeRelationDTO(
                nodeDto, nodeRelation.getRelationType(),
                relatedNodeDTO);
    }

}
