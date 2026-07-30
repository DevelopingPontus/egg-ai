package chasky.ai_gatherer.legacy.category;

import org.springframework.stereotype.Service;

import chasky.ai_gatherer.legacy.category.dto.CategoryResponse;

import java.io.IOException;

@Service
public class CategoryService {
    private final CategoryAiClient aiClient;
    // private final CategoryRepository repository;

    public CategoryService(CategoryAiClient aiClient) {
        this.aiClient = aiClient;
        // this.repository = repository;
    }

    public CategoryResponse promptAi(String prompt) throws IOException, InterruptedException {
        CategoryResponse response = aiClient.promptAi(prompt);
        // Optional<Node> node = repository.findById(new NodeId(response.category(), response.topic()));
        // if (node.isEmpty() || node == null) {
        //     repository.save(toNode(response));
        // }
        return response;
    }

    // private Node toNode(NodeResponse response) {
    //     return new Node(response.category(),
    //             response.topic(),
    //             response.topicsThisDependOn(),
    //             response.topicsThisEnables());
    // }
}
