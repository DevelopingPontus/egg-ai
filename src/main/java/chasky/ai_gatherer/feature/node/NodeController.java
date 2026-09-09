package chasky.ai_gatherer.feature.node;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.node.dto.NodeDTO;
import chasky.ai_gatherer.feature.node.entity.NodeId;
import chasky.ai_gatherer.feature.node.relation.dto.NodeRelationDTO;
import chasky.ai_gatherer.feature.node.request.NodeRequest;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {
    
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    // @EventListener(ApplicationReadyEvent.class)
    //         public void init() {
    //         System.out.println("Running on startup!");
    //         try {
    //             generateNodeWithRelations(new NodeRequest("devops roadmap"));
    //         } catch (Exception e) {
    //             System.out.println("Failed to run in startup");
    //         }
    //     }

    @PostMapping
    public List<NodeRelationDTO> generateNodeWithRelations(@Valid NodeRequest prompt)
            throws IOException, InterruptedException {
        return nodeService.generateNodeWithRelations(prompt);
    }

    @PostMapping("/expand")
    public List<NodeRelationDTO> expandNodeWithRelations(@Valid NodeDTO node)
            throws IOException, InterruptedException {
        return nodeService.expandNodeWithRelations(node);
    }
    
    @GetMapping
    public List<NodeDTO> getAllNodes()
            throws IOException, InterruptedException {
        return nodeService.getAllNodes();
    }


}
