package io.github.jchanaud;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@KafkaListener(groupId = "ticketDisasterApp", offsetReset = OffsetReset.LATEST)
public class TicketKafkaService {

    @Inject
    StatefulRedisConnection<String, String> redis;

    @Topic("tickets")
    public void receive(ConsumerRecord<String, String> record){

        if(record.serializedKeySize() > 50 || record.serializedValueSize() > 50)
            return;

        if(StringUtils.isEmpty(record.value()))
            return;

        var urlencoded = URLEncoder.encode(record.value(), StandardCharsets.UTF_8);

        // Mark ticket as ready in one hour from now
        var ticketNotReadyBefore = record.timestamp() + 3_600_000L;

        RedisCommands<String, String> commands = redis.sync();
        commands.set(urlencoded, String.valueOf(ticketNotReadyBefore));
    }
}
