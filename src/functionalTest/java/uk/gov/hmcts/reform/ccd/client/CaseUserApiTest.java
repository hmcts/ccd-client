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
        caseWorker = createCaseworker("autocaseworker-ccdclient@hmcts.net");
        manager = createCaseworker("autocaseworker-ccdclient3@hmcts.net");
    }

    @Test
    @DisplayName("Should be able to update case roles")
    void updateCaseRolesWhenRolesRemovedOrAdded() {
        User theCaseworker = createCaseworker("autocaseworker-ccdclient@hmcts.net");
        User manager = createCaseworker("autocaseworker-ccdclient3@hmcts.net");
        CaseDetails caseForCaseworker = createCaseForCaseworker(theCaseworker);

        caseAccessApi.grantAccessToCase(
                theCaseworker.getAuthToken(),
                authTokenGenerator.generate(),
                theCaseworker.getUserDetails().getId(),
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

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(manager.getUserDetails().getId())).isTrue();

        Set<String> newCaseRoles = new HashSet<>();
        caseUserApi.updateCaseRolesForUser(
                theCaseworker.getAuthToken(),
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

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(manager.getUserDetails().getId())).isFalse();

        newCaseRoles.add("[CREATOR]");

        caseUserApi.updateCaseRolesForUser(
                theCaseworker.getAuthToken(),
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

        assertThat(caseIdsGivenUserIdHasAccessTo.contains(manager.getUserDetails().getId())).isTrue();
    }
}
