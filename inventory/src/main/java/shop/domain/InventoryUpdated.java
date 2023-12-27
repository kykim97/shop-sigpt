package shop.domain;

import shop.domain.*;
import shop.infra.AbstractEvent;
import java.util.*;
import lombok.*;
import java.time.LocalDate;


//<<< DDD / Domain Event
@Data
@ToString
public class InventoryUpdated extends AbstractEvent {

    private Long productId;
    private Integer stockRemain;

    public InventoryUpdated(Inventory aggregate){
        super(aggregate);
    }
    public InventoryUpdated(){
        super();
    }
}
//>>> DDD / Domain Event