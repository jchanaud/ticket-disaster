package io.github.jchanaud;

import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Requires(notEnv="test") // This should only execute in test. -Junior DevOOPS
public class ChaosMonkeyService {

    private static final Logger log = LoggerFactory.getLogger(ChaosMonkeyService.class);
    @Inject
    StatefulRedisConnection<String, String> redis;

    @Scheduled(cron = "*/15 * * * *")
    public void clearCache(){
        redis.sync().flushall();
        log.info("🙊 Monkey clean data.");
    }
}
