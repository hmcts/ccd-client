package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.reform.ccd.client.mock.CcdWireMock;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
    CoreCaseDataClientAutoConfiguration.class,
    CoreCaseDataConfiguration.class,
    CoreCaseDataApi.class
})
@EnableAutoConfiguration
class CaseEventsApiTest {

    @Autowired
    private CaseEventsApi caseEventsApi;

    @BeforeAll
    static void configureSystemUnderTest() {
        CcdWireMock.start();
    }

    @AfterAll
    static void afterAll() {
        CcdWireMock.stopAndReset();
    }

    @Test
    @DisplayName("Should be able to call the get events Api")
    void getCaseEventsTest() throws IOException {
        String testUrl = "/caseworkers/1234/jurisdictions/jurisdictionId/case-types/moneyclaim/cases/caseId/events";
        CcdWireMock.stub(get(urlEqualTo(testUrl)), "caseEvents.json");
        List<CaseEventDetail> caseEventDetails = caseEventsApi
                .findEventDetailsForCase("UserToken", "s2sAuth", "1234", "jurisdictionId", "moneyclaim", "caseId");

        assertThat(caseEventDetails.size()).isEqualTo(4);
    }
}
