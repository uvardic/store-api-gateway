package store.server.gateway.item.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import store.server.gateway.item.dto.Item;
import store.server.gateway.item.service.ItemService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ItemQueryResolver implements GraphQLQueryResolver {

    private final ItemService itemService;

    public Item findItemById(Long id) {
        return itemService.findById(id);
    }

    public List<Item> findAllItems() {
        return itemService.findAll();
    }

}
