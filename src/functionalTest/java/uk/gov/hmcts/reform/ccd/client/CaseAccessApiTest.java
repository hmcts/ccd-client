package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.UserId;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@DisplayName("Case access API")
class CaseAccessApiTest extends BaseTest {

    @Autowired
    private CaseAccessApi caseAccessApi;

    private User caseWorker;
    private User manager;

    @BeforeEach
    void init() {
        caseWorker = createCaseworker();
        manager = createCaseworker();
    }

    @Test
    @DisplayName("Should be able to retrieve cases user has access to")
    void getCaseForCaseworker() {
        CaseDetails caseForCaseworker = createCaseForCaseworker(caseWorker);
        caseAccessApi.grantAccessToCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                caseForCaseworker.getId() + "",
                new UserId(manager.getUserDetails().getId())
        );

        List<String> caseIdsGivenUserIdHasAccessTo = caseAccessApi.findCaseIdsGivenUserIdHasAccessTo(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getId()
        );

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(caseForCaseworker.getId() + ""))
                .isTrue();
    }

    @Test
    @DisplayName("Should be able to revoke access to a case")
    void revokeAccessToCase() {
        CaseDetails caseForCaseworker = createCaseForCaseworker(caseWorker);
        caseAccessApi.grantAccessToCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                caseForCaseworker.getId() + "",
                new UserId(manager.getUserDetails().getId())
        );

        caseAccessApi.revokeAccessToCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                caseForCaseworker.getId() + "",
                manager.getUserDetails().getId()
        );

        List<String> caseIdsGivenUserIdHasAccessTo = caseAccessApi.findCaseIdsGivenUserIdHasAccessTo(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getId()
        );

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(manager.getUserDetails().getId())).isFalse();
    }
}
