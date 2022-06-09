package com.wbo.springkafkastarter;

import com.wbo.springkafkastarter.dtos.CompteDto;
import com.wbo.springkafkastarter.dtos.UserDto;
import com.wbo.springkafkastarter.service.KafkaListenerService;
import com.wbo.springkafkastarter.service.KafkaPublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(properties = {"topic.name=kafka-stream-test-user-infos"})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private KafkaPublisherService producer;

    @Autowired
    private KafkaListenerService consumer;


    @Test
    public void whenSendingOneMessageToProducer_thenMessageReceived()
            throws Exception {
        producer.send(build(101, "Adem 1", Arrays.asList(new CompteDto(10.0), new CompteDto(5.0))));
        TimeUnit.SECONDS.sleep(2);

        assertThat(consumer.getData().keys().hasMoreElements(), equalTo(true));
        assertThat(consumer.getData().values().stream().findFirst().isPresent(), equalTo(true));
        assertThat(consumer.getData().values().stream().findFirst().get().getId(), equalTo(101L));
        assertThat(consumer.getData().values().stream().findFirst().get().getName(), equalTo("Adem 1"));
        assertThat(consumer.getData().values().stream().findFirst().get().getComptes().size(), equalTo(2));


    }

    @Test
    public void whenSendingManyMessagesToProducer_thenMessagesReceived()
            throws Exception {
        producer.send(build(101, "Adem 1", Arrays.asList(new CompteDto(10.0), new CompteDto(5.0))));
        producer.send(build(102, "Adem 2", Arrays.asList(new CompteDto(30.0), new CompteDto(20.0))));
        TimeUnit.SECONDS.sleep(2);

        assertThat(consumer.total().get(101L), equalTo(15.0));
        assertThat(consumer.total().get(102L), equalTo(50.0));

    }

    private UserDto build(long id, String name, List<CompteDto> amounts) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setComptes(amounts);
        return userDto;
    }
}