package shop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import shop.domain.*;

@Entity
public class Inventory {

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
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publish();
    }
}
