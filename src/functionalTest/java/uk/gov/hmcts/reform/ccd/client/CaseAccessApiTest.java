package uk.gov.hmcts.reform.ccd.client;

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

    @Test
    @DisplayName("Should be able to retrieve cases user has access to")
    void getCaseForCaseworker() {
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
                theCaseworker.getAuthToken(),
                authTokenGenerator.generate(),
                theCaseworker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                manager.getUserDetails().getId()
        );

        assertThat(caseIdsGivenUserIdHasAccessTo.size())
                .isGreaterThan(0);
    }

}
