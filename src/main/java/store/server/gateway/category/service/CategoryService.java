package store.server.gateway.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import store.server.gateway.category.dto.Category;
import store.server.gateway.exception.CategoryServiceGraphQLError;
import store.server.gateway.exception.handler.CustomResponseErrorHandler;
import store.server.gateway.mapper.CustomObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String CATEGORY_ENDPOINT = "http://store-item-service/store/server/category";

    private final RestTemplate restTemplate;

    private final CustomResponseErrorHandler responseErrorHandler;

    private final CustomObjectMapper objectMapper;

    public Long deleteById(Long existingId) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/delete/existingId=%d", CATEGORY_ENDPOINT, existingId),
                HttpMethod.DELETE,
                null,
                Object.class
        );

        responseErrorHandler.handleError(response, CategoryServiceGraphQLError.class);

        return existingId;
    }

    public Category save(Category categoryRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/save", CATEGORY_ENDPOINT),
                HttpMethod.POST,
                new HttpEntity<>(categoryRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, CategoryServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Category.class);
    }

    public Category update(Long existingId, Category categoryRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/update/existingId=%d", CATEGORY_ENDPOINT, existingId),
                HttpMethod.PUT,
                new HttpEntity<>(categoryRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, CategoryServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Category.class);
    }

    public Category findById(Long id) {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/id=%d", CATEGORY_ENDPOINT, id), Object.class
        );

        responseErrorHandler.handleError(response, CategoryServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Category.class);
    }

    public List<Category> findAll() {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/all", CATEGORY_ENDPOINT), Object.class
        );

        responseErrorHandler.handleError(response, CategoryServiceGraphQLError.class);

        return objectMapper.mapResponseToList(response, Category.class);
    }

}
