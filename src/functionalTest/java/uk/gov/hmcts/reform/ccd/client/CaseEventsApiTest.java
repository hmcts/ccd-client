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
    private User manager;

    @BeforeEach
    void init() {
        caseWorker = createCaseworker();
        manager = createCaseworker();
    }

    @Test
    @DisplayName("Should be able to retrieve events for a case")
    void getCaseForCaseworker() {
        CaseDetails caseForCaseworker = createCaseForCaseworker(caseWorker);

        List<CaseEventDetail> caseEventDetails = caseEventsApi.findEventDetailsForCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                caseForCaseworker.getId() + ""
        );

        assertThat(caseEventDetails).isNotEmpty();
    }
}
