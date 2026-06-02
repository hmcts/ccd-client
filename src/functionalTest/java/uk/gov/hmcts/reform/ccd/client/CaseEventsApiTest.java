package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@DisplayName("Case events API")
class CaseEventsApiTest extends BaseTest {

    @Autowired
    private CaseEventsApi caseEventsApi;

    private User caseWorker;

    @BeforeEach
    void init() {
        caseWorker = createCaseworker();
    }

    @Test
    @DisplayName("Should be able to retrieve events for a case")
    void getEventsForCase() {
        CaseDetails caseForCaseworker = createCaseForCaseworker(caseWorker);

        List<CaseEventDetail> caseEventDetails = caseEventsApi.findEventDetailsForCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getUid(),
                JURISDICTION,
                CASE_TYPE,
                caseForCaseworker.getId().toString()
        );

        assertThat(caseEventDetails).isNotEmpty();
    }
}
