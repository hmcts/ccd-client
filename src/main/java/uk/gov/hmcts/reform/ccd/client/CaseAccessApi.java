package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.ccd.client.model.UserId;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "ccd-access-api", url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class)
public interface CaseAccessApi {

    @RequestMapping(
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/ids",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE
    )
    List<String> findCaseIdsGivenUserIdHasAccessTo(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader("ServiceAuthorization") String serviceAuthorization,
            @PathVariable("uid") final Integer uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @RequestParam(value = "userId") final String idSearchingFor
    );

    @RequestMapping(
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void grantAccessToCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader("ServiceAuthorization") String serviceAuthorization,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId,
            @RequestBody final UserId id
    );

    @RequestMapping(
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/users/{idToDelete}",
            method = RequestMethod.DELETE,
            consumes = MediaType.ALL_VALUE
    )
    void revokeAccessToCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader("ServiceAuthorization") String serviceAuthorization,
            @PathVariable("uid") final Integer uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId,
            @PathVariable("idToDelete") final String idToDelete
    );

}
