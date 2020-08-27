package uk.gov.hmcts.reform.ccd.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.common.io.Resources;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Classification;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
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
class CcdClientTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2018, 11, 01, 14, 37, 47, 584000000);
    private static WireMockServer wireMockServer;
    private static final String EVENT_TRIGGER_ID = "createCase";
    private static final String CASE_TYPE_ID = "TestAddressBookCase";

    @Autowired
    private CoreCaseDataApi ccdApi;

    @BeforeAll
    static void configureSystemUnderTest() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        System.setProperty("core_case_data.api.url", "http://localhost:" + wireMockServer.port() + "/");
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.shutdownServer();
    }

    private static String loadFile(String filename) throws IOException {
        return Resources.toString(ClassLoader.getSystemClassLoader().getResource(filename), UTF_8);
    }

    @Test
    @DisplayName("Should be able to call the v2 retreive Api")
    void getCaseTest() throws IOException {
        stubFor(get(urlEqualTo("/cases/1234"))
                .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
                .withHeader(AUTHORIZATION, equalTo("UserToken"))
                .withHeader("experimental", equalTo("true")
                )
                .willReturn(okJson(loadFile("case.v2.json")))
        );
        CaseDetails caseData = ccdApi.getCase("UserToken", "s2sAuth", "1234");

        assertThat(caseData.getJurisdiction()).isEqualTo("JURISDICTION");
        assertThat(caseData.getLastModified()).isEqualTo(DATE_TIME);
        assertThat(caseData.getCreatedDate()).isEqualTo(DATE_TIME);
        assertThat(caseData.getState()).isEqualTo("SOME_STATE");
        assertThat(caseData.getCaseTypeId()).isEqualTo("someCaseType");
        assertThat(caseData.getId()).isEqualTo(1234L);
        assertThat(caseData.getSecurityClassification()).isEqualTo(Classification.PUBLIC);
        Map<String, Object> someProp = (Map<String, Object>) caseData.getData().get("someProp");
        assertThat(someProp.get("someAttributes")).isEqualTo(1);

    }

    @Test
    @DisplayName("Should be able to call the v2 create a case Api")
    void startCaseTest() throws IOException {
        stubFor(get(urlEqualTo("/case-types/" + CASE_TYPE_ID + "/event-triggers/" + EVENT_TRIGGER_ID))
                .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
                .withHeader(AUTHORIZATION, equalTo("UserToken"))
                .withHeader("experimental", equalTo("true")
                )
                .willReturn(okJson(loadFile("startCase.v2.json")))
        );
        StartEventResponse startEvent = ccdApi.startCase("UserToken", "s2sAuth", CASE_TYPE_ID, EVENT_TRIGGER_ID);

        CaseDetails caseDetails = startEvent.getCaseDetails();
        assertThat(caseDetails.getCaseTypeId()).isEqualTo(CASE_TYPE_ID);
        assertThat(caseDetails.getLastModified()).isEqualTo(DATE_TIME);
        assertThat(caseDetails.getCreatedDate()).isEqualTo(DATE_TIME);
        assertThat(caseDetails.getState()).isEqualTo("SOME_STATE");
        assertThat(caseDetails.getId()).isEqualTo(1234L);
        assertThat(caseDetails.getSecurityClassification()).isEqualTo(Classification.PUBLIC);
        Map<String, Object> someProp = (Map<String, Object>) caseDetails.getData().get("someProp");
        assertThat(someProp.get("someAttributes")).isEqualTo(1);

    }

    @Test
    @DisplayName("Should be able to call the search result api")
    void searchResultTest() throws IOException {
        stubFor(post(urlEqualTo("/searchCases?ctid=" + CASE_TYPE_ID))
                .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
                .withHeader(AUTHORIZATION, equalTo("UserToken"))
                .willReturn(okJson(loadFile("searchResult.json")))
        );
        SearchResult startEvent = ccdApi.searchCases("UserToken", "s2sAuth", CASE_TYPE_ID, "");

        int total = startEvent.getTotal();
        assertThat(total).isEqualTo(10);
    }
}
