package de.awtools.registration.register;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import de.awtools.registration.config.RegisterTestConfig;
import de.awtools.registration.mail.SendMailService;

/**
 * Tries to send an emai.
 *
 * @author Andre Winkler
 */
@RegisterTestConfig
@WebAppConfiguration
class SendMailTest {

    @Autowired
    private SendMailService sendMailService;

    @Disabled
    @Test
    void sendMail() throws Exception {
        sendMailService.sendMail("donotreply@wp1057914.server-he.de",
                "mail@andre-winkler.de",
                "Register DB test", "This is a link.");
    }

}
