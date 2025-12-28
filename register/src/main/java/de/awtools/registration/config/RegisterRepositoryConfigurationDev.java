package de.awtools.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("dev")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "file:${user.home}/.awitools/.register.properties",
})
public class RegisterRepositoryConfigurationDev {

}
