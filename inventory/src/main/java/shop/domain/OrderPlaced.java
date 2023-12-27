package shop.domain;

import java.util.*;
import lombok.*;
import shop.domain.*;
import shop.infra.AbstractEvent;

@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long productId;
    private Integer qty;

    public void processInventoryUpdate() {
        Inventory inventory = new Inventory();
        inventory.updateInventory(this);
    }
}
