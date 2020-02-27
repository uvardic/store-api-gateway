package store.server.gateway.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import store.server.gateway.exception.ItemServiceGraphQLError;
import store.server.gateway.exception.handler.CustomResponseErrorHandler;
import store.server.gateway.item.dto.Item;
import store.server.gateway.mapper.CustomObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private static final String ITEM_ENDPOINT = "http://store-item-service/store/server/item";

    private final RestTemplate restTemplate;

    private final CustomResponseErrorHandler responseErrorHandler;

    private final CustomObjectMapper objectMapper;

    public Long deleteById(Long existingId) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/delete/existingId=%d", ITEM_ENDPOINT, existingId),
                HttpMethod.DELETE,
                null,
                Object.class
        );

        responseErrorHandler.handleError(response, ItemServiceGraphQLError.class);

        return existingId;
    }

    public Item save(Item itemRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/save", ITEM_ENDPOINT),
                HttpMethod.POST,
                new HttpEntity<>(itemRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, ItemServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Item.class);
    }

    public Item update(Long existingId, Item itemRequest) {
        ResponseEntity<?> response = restTemplate.exchange(
                String.format("%s/update/existingId=%d", ITEM_ENDPOINT, existingId),
                HttpMethod.PUT,
                new HttpEntity<>(itemRequest),
                Object.class
        );

        responseErrorHandler.handleError(response, ItemServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Item.class);
    }

    public Item findById(Long id) {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/id=%d", ITEM_ENDPOINT, id), Object.class
        );

        responseErrorHandler.handleError(response, ItemServiceGraphQLError.class);

        return objectMapper.mapResponse(response, Item.class);
    }

    public List<Item> findAll() {
        ResponseEntity<?> response = restTemplate.getForEntity(
                String.format("%s/all", ITEM_ENDPOINT), Object.class
        );

        responseErrorHandler.handleError(response, ItemServiceGraphQLError.class);

        return objectMapper.mapResponseToList(response, Item.class);
    }

}
