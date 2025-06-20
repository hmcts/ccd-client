package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.ccd.client.healthcheck.InternalHealth;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.CaseResource;
import uk.gov.hmcts.reform.ccd.client.model.CategoriesAndDocuments;
import uk.gov.hmcts.reform.ccd.client.model.PaginatedSearchMetadata;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        name = "core-case-data-api",
        primary = false,
        url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class
)
public interface CoreCaseDataApi {
    String SERVICE_AUTHORIZATION = "ServiceAuthorization";
    String EXPERIMENTAL = "experimental=true";

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
                    + "{eventId}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @PathVariable("eventId") String eventId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
                    + "{eventId}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @PathVariable("eventId") String eventId,
            @RequestParam("ignore-warning") boolean ignoreWarning
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    CaseDetails submitForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/searchCases?ctid={caseType}",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    SearchResult searchCases(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("caseType") String caseType,
            @RequestBody String searchString
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    List<CaseDetails> searchForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
                    + "/pagination_metadata",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE)
    PaginatedSearchMetadata getPaginationInfoForSearchForCaseworkers(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/event-triggers/{etid}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startEventForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @PathVariable("etid") String eventId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/event-triggers/{etid}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startEventForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @PathVariable("etid") String eventId,
            @RequestParam("ignore-warning") boolean ignoreWarning
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/events"
    )
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

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases/pagination_metadata",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE)
    PaginatedSearchMetadata getPaginationInfoForSearchForCitizens(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
                    + "{eventId}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @PathVariable("eventId") String eventId
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    CaseDetails submitForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/event-triggers/{etid}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startEventForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("uid") String userId,
            @PathVariable("jid") String jurisdictionId,
            @PathVariable("ctid") String caseType,
            @PathVariable("cid") String caseId,
            @PathVariable("etid") String eventId
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}/events"
    )
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

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    List<CaseDetails> searchForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("userId") String userId,
            @PathVariable("jurisdictionId") String jurisdictionId,
            @PathVariable("caseType") String caseType,
            @RequestParam Map<String, String> searchCriteria
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    CaseDetails readForCaseWorker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/citizens/{uid}/jurisdictions/{jid}/case-types/{ctid}/cases/{cid}",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    CaseDetails readForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("uid") final String uid,
            @PathVariable("jid") final String jurisdictionId,
            @PathVariable("ctid") final String caseTypeId,
            @PathVariable("cid") final String caseId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/status/health",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    InternalHealth health();

    // ==========================================
    // V2 Api's
    // ==========================================

    @GetMapping(
            path = "/cases/{cid}",
            headers = EXPERIMENTAL
    )
    CaseDetails getCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("cid") String caseId
    );

    @GetMapping(
            path = "/case-types/{caseTypeId}/event-triggers/{triggerId}",
            headers = EXPERIMENTAL
    )
    StartEventResponse startCase(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("caseTypeId") String caseType,
            @PathVariable("triggerId") String eventId
    );

    @PostMapping(
            path = "/case-types/{caseTypeId}/cases",
            headers = EXPERIMENTAL
    )
    CaseDetails submitCaseCreation(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("caseTypeId") String caseType,
            @RequestBody CaseDataContent caseDataContent);

    @GetMapping(
            path = "/cases/{caseId}/event-triggers/{triggerId}",
            headers = EXPERIMENTAL
    )
    StartEventResponse startEvent(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable("caseId") String caseId,
            @PathVariable("triggerId") String eventId
    );

    @PostMapping(
            path = "/cases/{caseId}/events",
            headers = EXPERIMENTAL
    )
    CaseResource createEvent(@RequestHeader(AUTHORIZATION) String userAuthorizationHeader,
                             @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
                             @PathVariable("caseId") String caseId,
                             @RequestBody CaseDataContent caseDataContent);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cases/{caseId}/supplementary-data"
    )
    CaseDetails submitSupplementaryData(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable("caseId") String caseId,
            @RequestBody Map<String, Map<String, Map<String, Object>>> supplementaryData
    );

    @GetMapping(path = "/categoriesAndDocuments/{caseRef}", headers = EXPERIMENTAL)
    CategoriesAndDocuments getCategoriesAndDocuments(
                    @RequestHeader(AUTHORIZATION) String authorisation,
                    @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
                    @PathVariable("caseRef") String caseRef);
}
