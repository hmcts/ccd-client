package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.gov.hmcts.reform.ccd.client.model.CaseUser;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi.SERVICE_AUTHORIZATION;

@FeignClient(name = "ccd-user-api", url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class)
public interface CaseUserApi {

    @PutMapping("/cases/{caseReference}/users/{userId}")
    void updateCaseRolesForUser(
        @RequestHeader(AUTHORIZATION) String authorisation,
        @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
        @PathVariable("caseReference") String caseReference,
        @PathVariable("userId") String userId,
        @RequestBody CaseUser caseUser
    );
}
