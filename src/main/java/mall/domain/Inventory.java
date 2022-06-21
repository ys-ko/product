package mall.domain;

import javax.persistence.*;
import lombok.Data;
import mall.ProductApplication;

@Entity
@Table(name = "Inventory_table")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productId;
    private String productName;
    private Integer stock;

    @PostUpdate
    public void onPostUpdate() {
        InventoryModified inventoryModified = new InventoryModified(this);
        inventoryModified.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = ProductApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    public static void inventoryDecrease(DeliveryStarted deliveryStarted) {
        repository().findByProductId(deliveryStarted.getProductId()).ifPresent(inventory->{
            inventory.setStock(inventory.getStock() - deliveryStarted.getQty());
            repository().save(inventory);
         });
    }

    public static void inventoryIncrease(DeliveryCanceled deliveryCanceled) {
        repository().findByProductId(deliveryCanceled.getProductId()).ifPresent(inventory->{
            inventory.setStock(inventory.getStock() + deliveryCanceled.getQty());
            repository().save(inventory);
         });
    }
}
