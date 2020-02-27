package store.server.gateway.item.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import store.server.gateway.item.dto.Item;
import store.server.gateway.item.service.ItemService;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ItemMutationResolver implements GraphQLMutationResolver {

    private final ItemService itemService;

    public Long deleteItemById(Long existingId) {
        return itemService.deleteById(existingId);
    }

    public Item saveItem(Item itemRequest) {
        return itemService.save(itemRequest);
    }

    public Item updateItem(Long existingId, Item itemRequest) {
        return itemService.update(existingId, itemRequest);
    }

}
