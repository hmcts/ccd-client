package uk.gov.hmcts.reform.ccd.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Classification;

import java.time.LocalDateTime;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(classes = {CoreCaseDataClientAutoConfiguration.class, CoreCaseDataConfiguration.class, CoreCaseDataApi.class})
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
class CcdClientTest {


    public static final LocalDateTime DATE_TIME = LocalDateTime.of(2018, 11, 01, 14, 37, 47, 584000000);
    private static WireMockServer wireMockServer;

    @Autowired
    CoreCaseDataApi ccdApi;

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

    @SneakyThrows
    public static String loadFile(String filename) {
        return Resources.toString(ClassLoader.getSystemClassLoader().getResource(filename), UTF_8);
    }

    @Test
    @DisplayName("Should be able to call the new Api")
    void getCaseTest() {
        stubFor(get(urlEqualTo("/cases/1234"))
                .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
                .withHeader(AUTHORIZATION, equalTo("UserToken"))
                .withHeader("experimental", equalTo("")
                )
                .willReturn(okJson(loadFile("v2Case.json")))
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
}
