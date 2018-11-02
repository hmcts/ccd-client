package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.ccd.client.healthcheck.InternalHealth;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.PaginatedSearchMetadata;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(
        name = "core-case-data-api",
        url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class
)
public interface CoreCaseDataApi {
    String SERVICE_AUTHORIZATION = "ServiceAuthorization";
    String EXPERIMENTAL = "experimental=";

    @GetMapping("/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
            + "{eventId}/token")
    StartEventResponse startForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @PathVariable("eventId") String eventId
    );

    @PostMapping("/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases")
    CaseDetails submitForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @GetMapping("/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases")
    List<CaseDetails> searchForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @GetMapping("/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases/pagination_metadata")
    PaginatedSearchMetadata getPaginationInfoForSearchForCaseworkers(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @GetMapping("/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/event-triggers/{etid}/token")
    StartEventResponse startEventForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @PathVariable("etid") String eventId
    );

    @PostMapping("/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/events")
    CaseDetails submitEventForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @GetMapping("/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases/pagination_metadata")
    PaginatedSearchMetadata getPaginationInfoForSearchForCitizens(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );


    @GetMapping("/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}" +
            "/event-triggers/{eventId}/token")
    StartEventResponse startForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @PathVariable("eventId") String eventId
    );

    @PostMapping("/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases")
    CaseDetails submitForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @GetMapping("/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/event-triggers/{etid}/token")
    StartEventResponse startEventForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @PathVariable("etid") String eventId
    );

    @PostMapping("/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/events")
    CaseDetails submitEventForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @GetMapping("/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases")
    List<CaseDetails> searchForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @GetMapping("/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}")
    CaseDetails readForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId
    );

    @GetMapping("/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}")
    CaseDetails readForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId
    );

    @GetMapping("/status/health")
    InternalHealth health();

    // ==========================================
    // V2 Api's
    // ==========================================

    /**
     * Retrieve case by Case Id
     */
    @GetMapping(
            path = "/cases/{cid}",
            headers = EXPERIMENTAL
    )
    CaseDetails getCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("cid") String caseId
    );


}
