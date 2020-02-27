package store.server.gateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private Double value;

    private Currency currency;

    private Integer discount;

    private Double discountedValue;

    private boolean onSale;

}
