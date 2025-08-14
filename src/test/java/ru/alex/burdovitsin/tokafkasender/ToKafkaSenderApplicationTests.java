package ru.alex.burdovitsin.tokafkasender;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import ru.alex.burdovitsin.tokafkasender.controller.OwnKafkaWaiter;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ToKafkaSenderApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private OwnKafkaWaiter waiter;

    @BeforeEach
    void setup() {
        waiter.resetLatch();
    }

    @Test
    public void async() throws Exception {
        String data = "Sending with default template";

        template.send("test_1.0", data);

        boolean messageConsumed = waiter.getLatch()
                .await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(waiter.getPayload(), CoreMatchers.containsString(data));
    }
}
