package shop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import shop.domain.*;

@Entity
public class Inventory {

    @Autowired
    InventoryRepository repository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
