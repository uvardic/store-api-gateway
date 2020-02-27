package store.server.gateway.category.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import store.server.gateway.category.dto.Category;
import store.server.gateway.category.service.CategoryService;

import java.util.List;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CategoryQueryResolver implements GraphQLQueryResolver {

    private final CategoryService categoryService;

    public Category findCategoryById(Long id) {
        return categoryService.findById(id);
    }

    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

}
