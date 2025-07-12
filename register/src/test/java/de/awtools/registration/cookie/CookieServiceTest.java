package de.awtools.registration.cookie;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;

import de.awtools.registration.config.RegisterTestConfig;

@WebAppConfiguration
@RegisterTestConfig
@Transactional
@Rollback
public class CookieServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(CookieServiceTest.class);

    @Autowired
    private CookieService cookieService;

    @Test
    public void registerNewAccount() {
        LOG.info("Start of the test...");

        CookieEntity cookie = cookieService.storeCookieAcceptance("test.de",
                "Bworser",
                "RemoteAddress", false);
        assertThat(cookie.isAcceptingCookie()).isFalse();

        CookieEntity cookie2 = cookieService.storeCookieAcceptance("test.de",
                "Bworser",
                "RemoteAddress", true);
        assertThat(cookie2.isAcceptingCookie()).isTrue();
    }

}
