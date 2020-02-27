package store.server.gateway.category.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import store.server.gateway.category.dto.Category;
import store.server.gateway.category.service.CategoryService;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CategoryMutationResolver implements GraphQLMutationResolver {

    private final CategoryService categoryService;

    public Long deleteCategoryById(Long existingId) {
        return categoryService.deleteById(existingId);
    }

    public Category saveCategory(Category categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    public Category updateCategory(Long existingId, Category categoryRequest) {
        return categoryService.update(existingId, categoryRequest);
    }

}
