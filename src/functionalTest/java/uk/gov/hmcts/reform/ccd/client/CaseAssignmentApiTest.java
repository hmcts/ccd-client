package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRole;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRoleWithOrganisation;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesRequest;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesResource;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesResponse;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@DisplayName("Case assignment API")
class CaseAssignmentApiTest extends BaseTest {

    @Autowired
    private CaseAssignmentApi caseAssignmentApi;

    private User caseWorker;
    private User manager;
    private CaseDetails caseDetails;
    private CaseAssignmentUserRolesRequest caseAssignmentRequest;

    @BeforeEach
    void init() {
        caseWorker = createCaseworker();
        manager = createCaseworker();
        caseDetails = createCaseForCaseworker(caseWorker);
        caseAssignmentRequest = CaseAssignmentUserRolesRequest.builder()
                .caseAssignmentUserRolesWithOrganisation(Collections.singletonList(
                        CaseAssignmentUserRoleWithOrganisation.builder()
                                .organisationId("TESTTORG")
                                .caseDataId(caseDetails.getId().toString())
                                .userId(caseWorker.getUserDetails().getId())
                                .caseRole("[TESTSOLICITOR]")
                                .build()))
                .build();
    }

    @Test
    @DisplayName("Should be able to add case user roles")
    void addCaseUserRoles() {
        CaseAssignmentUserRolesResponse response = caseAssignmentApi.addCaseUserRoles(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseAssignmentRequest
        );

        assertThat(response.getStatusMessage()).isEqualTo("Case-User-Role assignments created successfully");
    }

    @Test
    @DisplayName("Should be able to retrieve case user roles")
    void getUserRoles() {
        CaseAssignmentUserRole expectedCaseAssignmentUserRole = CaseAssignmentUserRole.builder()
                .caseDataId(caseDetails.getId().toString())
                .userId(caseWorker.getUserDetails().getId())
                .caseRole("[TESTSOLICITOR]")
                .build();

        caseAssignmentApi.addCaseUserRoles(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseAssignmentRequest
        );

        CaseAssignmentUserRolesResource resource = caseAssignmentApi.getUserRoles(
                manager.getAuthToken(),
                authTokenGenerator.generate(),
                Collections.singletonList(caseDetails.getId().toString())
        );

        assertThat(resource.getCaseAssignmentUserRoles()).containsOnly(expectedCaseAssignmentUserRole);
    }

    @Test
    @DisplayName("Should be able to remove case user roles")
    void removeCaseUserRoles() {
        CaseAssignmentUserRole expectedCaseAssignmentUserRole = CaseAssignmentUserRole.builder()
                .caseDataId(caseDetails.getId().toString())
                .userId(caseWorker.getUserDetails().getId())
                .caseRole("[TESTSOLICITOR]")
                .build();

        caseAssignmentApi.addCaseUserRoles(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseAssignmentRequest
        );

        caseAssignmentApi.removeCaseUserRoles(
                caseWorker.getAuthToken(),
                authTokenGenerator.generate(),
                caseAssignmentRequest
        );

        CaseAssignmentUserRolesResource resource = caseAssignmentApi.getUserRoles(
                manager.getAuthToken(),
                authTokenGenerator.generate(),
                Collections.singletonList(caseDetails.getId().toString())
        );

        assertThat(resource.getCaseAssignmentUserRoles()).doesNotContain(expectedCaseAssignmentUserRole);
    }
}
