package io.github.jchanaud;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Map;


@Controller("/")
public class TicketController {

    @Inject
    StatefulRedisConnection<String, String> redis;

    @Value("${flag}")
    String FLAG;

    @View("index")
    @Get("/tickets/{ticket}")
    public Map<String, Object> getTicket(String ticket){
        RedisCommands<String, String> commands = redis.sync();
        String result = commands.get(ticket);

        if(result == null)
            return Map.of("ticket", ticket, "response","Error: Ticket Request not found / expired");

        var ticketNotReadyBefore = Instant.ofEpochMilli(Long.parseLong(result));
        var now = Instant.now();

        if(now.toEpochMilli() < ticketNotReadyBefore.toEpochMilli())
            return Map.of("ticket", ticket, "response","Error: Ticket will be ready available at " + ticketNotReadyBefore);

        return Map.of("ticket", ticket, "response", "Success: flag{"+FLAG+"}");
        // Template is at src/main/resources/views/index.jte
    }


    @View("index")
    @Get()
    public Map<String, Object> index(){
        return Map.of();
    }
}
