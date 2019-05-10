package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.ccd.client.model.UserId;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi.SERVICE_AUTHORIZATION;

@FeignClient(name = "ccd-access-api", url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class)
public interface CaseAccessApi {

    @GetMapping("/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/ids")
    List<String> findCaseIdsGivenUserIdHasAccessTo(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @RequestParam(value = "userId") final String idSearchingFor
    );

    @PostMapping(
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/users",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void grantAccessToCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId,
            @RequestBody final UserId id
    );

    @DeleteMapping("/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/users/{idToDelete}")
    void revokeAccessToCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId,
            @PathVariable("idToDelete") final String idToDelete
    );
}