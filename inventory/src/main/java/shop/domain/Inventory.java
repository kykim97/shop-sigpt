package shop.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import shop.InventoryApplication;
import shop.domain.InventoryUpdated;

@Entity
@Table(name = "Inventory_table")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private Integer stockRemain;

    @PostPersist
    public void onPostPersist() {
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    public static Inventory updateInventory(OrderPlaced orderPlaced) {
        Optional<Inventory> optionalInventory = repository()
            .findById(orderPlaced.getProductId());
        if (!optionalInventory.isPresent()) return null;

        Inventory inventory = optionalInventory.get();
        inventory.setStockRemain(
            (
                inventory.getStockRemain() != null
                    ? inventory.getStockRemain()
                    : 0
            ) -
            orderPlaced.getQty()
        );
        repository().save(inventory);

        InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
        inventoryUpdated.publishAfterCommit();

        return inventory;
    }
}
