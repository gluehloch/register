package de.awtools.registration.mail;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

public class MailControllerTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Test
    @DisplayName("Send test")
    void testSend() {
        GreenMailUtil.sendTextEmailTest("to@localhost", "from@localhost", "some subject", "some body");
        final MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        final MimeMessage receivedMessage = receivedMessages[0];
        assertThat(GreenMailUtil.getBody(receivedMessage)).isEqualTo("some body");
    }

}
