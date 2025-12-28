package de.awtools.registration.logger;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;

import de.awtools.registration.config.RegisterTestConfig;

@RegisterTestConfig
@WebAppConfiguration
@Transactional
@Rollback
class LoggerTest {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerTest.class);

    @Autowired
    Environment environment;

    @Test
    void printLoggerStatus() {
        String join = String.join(",", environment.getActiveProfiles());
        LOG.info("Environment: {}", join);
        // LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // StatusPrinter.print(lc);
    }
}
