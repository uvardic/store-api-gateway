package store.server.gateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.server.gateway.category.dto.Category;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;

    private String name;

    private String description;

    private Price price;

    private String postDate;

    private Set<Category> categories = new HashSet<>();

}
