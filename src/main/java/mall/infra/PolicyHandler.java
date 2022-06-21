package mall.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import mall.config.kafka.KafkaProcessor;
import mall.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    InventoryRepository inventoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryStarted_InventoryDecrease(
        @Payload DeliveryStarted deliveryStarted
    ) {
        if (!deliveryStarted.validate()) return;
        DeliveryStarted event = deliveryStarted;
        System.out.println(
            "\n\n##### listener InventoryDecrease : " +
            deliveryStarted.toJson() +
            "\n\n"
        );

        // Sample Logic //
        Inventory.inventoryDecrease(event);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_InventoryIncrease(
        @Payload DeliveryCanceled deliveryCanceled
    ) {
        if (!deliveryCanceled.validate()) return;
        DeliveryCanceled event = deliveryCanceled;
        System.out.println(
            "\n\n##### listener InventoryIncrease : " +
            deliveryCanceled.toJson() +
            "\n\n"
        );

        // Sample Logic //
        Inventory.inventoryIncrease(event);
    }
    // keep

}
