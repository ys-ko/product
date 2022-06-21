package mall.domain;

import java.util.Date;
import lombok.Data;
import mall.domain.*;
import mall.infra.AbstractEvent;

@Data
public class InventoryModified extends AbstractEvent {

    private Long id;
    private String productId;
    private String productName;
    private Integer stock;

    public InventoryModified(Inventory aggregate) {
        super(aggregate);
    }

    public InventoryModified() {
        super();
    }
    // keep

}
