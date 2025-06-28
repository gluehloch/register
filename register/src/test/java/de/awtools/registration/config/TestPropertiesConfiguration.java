package de.awtools.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "classpath:/register-test.properties",
        "file:${user.home}/.register-test.properties",
})
public class TestPropertiesConfiguration {

}
