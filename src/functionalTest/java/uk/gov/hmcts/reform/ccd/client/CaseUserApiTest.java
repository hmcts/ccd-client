package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.CaseUser;
import uk.gov.hmcts.reform.ccd.client.model.UserId;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@DisplayName("Case user API")
class CaseUserApiTest extends BaseTest {

    @Autowired
    private CaseUserApi caseUserApi;

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
    @DisplayName("Should be able to update case roles")
    void updateCaseRolesWhenRolesRemovedOrAdded() {
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

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(caseForCaseworker.getId().toString())).isTrue();

        Set<String> newCaseRoles = new HashSet<>();
        caseUserApi.updateCaseRolesForUser(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseForCaseworker.getId() + "",
                manager.getUserDetails().getId(),
                new CaseUser(manager.getUserDetails().getId(), newCaseRoles)
        );

        caseIdsGivenUserIdHasAccessTo = caseAccessApi.findCaseIdsGivenUserIdHasAccessTo(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getId()
        );
        assertThat(caseIdsGivenUserIdHasAccessTo.contains(caseForCaseworker.getId().toString())).isFalse();

        newCaseRoles.add("[CREATOR]");

        caseUserApi.updateCaseRolesForUser(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseForCaseworker.getId() + "",
                manager.getUserDetails().getId(),
                new CaseUser(manager.getUserDetails().getId(), newCaseRoles)
        );

        caseIdsGivenUserIdHasAccessTo = caseAccessApi.findCaseIdsGivenUserIdHasAccessTo(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getId()
        );

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(caseForCaseworker.getId().toString())).isTrue();
    }
}
