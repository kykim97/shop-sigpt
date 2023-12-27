package shop.domain;

import shop.domain.*;
import shop.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long productId;
    private Integer qty;
}


