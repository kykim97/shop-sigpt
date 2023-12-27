package shop.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import shop.domain.*;
import shop.infra.AbstractEntity;

public class Inventory extends AbstractEntity {

    @Autowired
    InventoryRepository repository;

    private Long productId;
    private Integer stockRemain;

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setStockRemain(Integer stockRemain) {
        this.stockRemain = stockRemain;
    }

    public void updateInventory(OrderPlaced orderPlaced) {
        this.productId = orderPlaced.getProductId();
        this.stockRemain = this.stockRemain - orderPlaced.getQty();
        repository.save(this);
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publish();
    }
}
