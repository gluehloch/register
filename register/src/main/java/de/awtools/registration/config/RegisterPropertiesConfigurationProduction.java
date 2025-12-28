package de.awtools.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("production")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "file:${AWTOOLS_CONFDIR}/register/register.properties"
})
public class RegisterPropertiesConfigurationProduction {

}
