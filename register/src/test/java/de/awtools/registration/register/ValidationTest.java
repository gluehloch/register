package de.awtools.registration.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.ServletContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.awtools.registration.config.RegisterTestConfig;
import de.awtools.registration.user.ApplicationEntity;
import de.awtools.registration.user.ApplicationRepository;

@RegisterTestConfig
@WebAppConfiguration
@Rollback
class ValidationTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppContext)
                .build();
    }

    @AfterEach
    void teardown() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void ping() throws Exception {
        ServletContext context = webAppContext.getServletContext();

        assertThat(context).isNotNull().isInstanceOf(MockServletContext.class);
        assertThat(context.getServletContextName())
                .isEqualTo("MockServletContext");
        assertThat(webAppContext.getBean(RegistrationController.class))
                .isNotNull();

        MvcResult result = mockMvc
                .perform(get("/ping")
                        .header("Content-type", "application/json")
                        .header("charset", "UTF-8"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentType())
                .isEqualTo("application/json;charset=utf-8");
    }

    @Test
    void validateUnknownApplication() throws Exception {
        // ServletContext context = webAppContext.getServletContext();

        RegistrationJson registration = new RegistrationJson();
        registration.setApplicationName("unknownApplication");
        registration.setEmail("email@web.de");
        registration.setFirstname("firstname");
        registration.setName("name");
        registration.setNickname("nickname");
        registration.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(registration);

        /*MvcResult result =*/
        mockMvc
                .perform(post("/registration/validate")
                        .content(json)
                        .header("Content-type", "application/json")
                        .header("charset", "UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(
                        jsonPath("validationCode").value("UNKNOWN_APPLICATION"))
                .andExpect(content().json(
                        "{'nickname':'nickname'," +
                                "'applicationName':'unknownApplication'," +
                                "'validationCode':['UNKNOWN_APPLICATION']}"))
                .andReturn();
    }

    @Test
    void validateKnownApplication() throws Exception {
        applicationRepository.deleteAll();

        ApplicationEntity application = new ApplicationEntity();
        application.setName("application");
        application.setDescription("Test Application for some JUnit tests.");
        application = applicationRepository.save(application);

        RegistrationJson registration = new RegistrationJson();
        registration.setApplicationName("application");
        registration.setEmail("email@web.de");
        registration.setFirstname("firstname");
        registration.setName("name");
        registration.setNickname("nickname");
        registration.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(registration);

        mockMvc.perform(post("/registration/validate")
                .content(json)
                .header("Content-type", "application/json")
                .header("charset", "UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("nickname"))
                .andExpect(jsonPath("validationCodes").value("OK"))
                .andExpect(content().json(
                        "{'nickname':'nickname'," +
                                "'applicationName':'application'," +
                                "'validationCodes':['OK']}"))
                .andReturn();

        applicationRepository.delete(application);
    }

}
