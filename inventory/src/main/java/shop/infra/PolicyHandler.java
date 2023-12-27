package shop.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import shop.config.kafka.KafkaProcessor;
import shop.domain.*;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    InventoryRepository inventoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlaced'"
    )
    public void wheneverOrderPlaced_UpdateInventory(
        @Payload OrderPlaced orderPlaced
    ) {
        try {
            OrderPlaced event = orderPlaced;
            System.out.println(
                "\n\n##### listener UpdateInventory : " + orderPlaced + "\n\n"
            );

            // Sample Logic //
            Inventory inventory = Inventory.updateInventory(event);
            Assert.isTrue(inventory != null, "Inventory must exist");
            InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
            inventoryUpdated.publish();
        } catch (Exception e) {
            // exception handling
            System.out.println(
                "Error occurred while processing inventory update."
            );
        }
    }
}
