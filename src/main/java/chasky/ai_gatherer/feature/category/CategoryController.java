package chasky.ai_gatherer.feature.category;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chasky.ai_gatherer.feature.category.dto.CategoryRequest;
import chasky.ai_gatherer.feature.category.dto.CategoryResponse;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse postMethodName(CategoryRequest prompt) throws IOException, InterruptedException {
        return categoryService.promptAi(prompt.descriptionOfCategory());
    }
}
