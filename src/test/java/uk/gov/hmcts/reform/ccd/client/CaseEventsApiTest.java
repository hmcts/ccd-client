package uk.gov.hmcts.reform.ccd.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.common.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(classes = {
    CoreCaseDataClientAutoConfiguration.class,
    CoreCaseDataConfiguration.class,
    CoreCaseDataApi.class
})
@EnableAutoConfiguration
class CaseEventsApiTest {
    private static WireMockServer wireMockServer;

    @Autowired
    private CaseEventsApi caseEventsApi;

    @Before
    static void configureSystemUnderTest() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        System.setProperty("core_case_data.api.url", "http://localhost:" + wireMockServer.port() + "/");
    }

    @After
    static void afterAll() {
        wireMockServer.shutdownServer();
    }

    private static String loadFile(String filename) throws IOException {
        return Resources.toString(ClassLoader.getSystemClassLoader().getResource(filename), UTF_8);
    }

    @Test
    @DisplayName("Should be able to call the get events Api")
    void getCaseEventsTest() throws IOException {
        String testUrl = "/caseworkers/1234/jurisdictions/jurisdictionId/case-types/moneyclaim/cases/caseId/events";
        stubFor(get(urlEqualTo(testUrl))
            .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
            .withHeader(AUTHORIZATION, equalTo("UserToken"))
            .willReturn(okJson(loadFile("caseEvents.json")))
        );
        List<CaseEventDetail> caseEventDetails = caseEventsApi
                .findEventDetailsForCase("UserToken", "s2sAuth", "1234", "jurisdictionId", "moneyclaim", "caseId");

        assertThat(caseEventDetails.size()).isEqualTo(4);
    }
}
