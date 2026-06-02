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
        String caseId = caseForCaseworker.getId().toString();
        String managerId = manager.getUserDetails().getUid();

        caseAccessApi.grantAccessToCase(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getUid(),
                JURISDICTION,
                CASE_TYPE,
                caseId,
                new UserId(managerId)
        );

        assertThat(findCaseIdsGivenManagerHasAccessTo())
                .contains(caseId);

        Set<String> newCaseRoles = new HashSet<>();

        caseUserApi.updateCaseRolesForUser(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseId,
                managerId,
                new CaseUser(managerId, newCaseRoles)
        );

        assertThat(findCaseIdsGivenManagerHasAccessTo())
                .doesNotContain(caseId);

        newCaseRoles.add("[CREATOR]");

        caseUserApi.updateCaseRolesForUser(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseId,
                managerId,
                new CaseUser(managerId, newCaseRoles)
        );

        assertThat(findCaseIdsGivenManagerHasAccessTo())
                .contains(caseId);
    }

    private List<String> findCaseIdsGivenManagerHasAccessTo() {
        return caseAccessApi.findCaseIdsGivenUserIdHasAccessTo(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseWorker.getUserDetails().getUid(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getUid()
        );
    }
}
