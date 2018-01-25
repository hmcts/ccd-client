package uk.gov.hmcts.reform.ccd.client;

import feign.QueryMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.ccd.client.healthcheck.InternalHealth;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "core-case-data-api", url = "${core_case_data.api.url}",
        configuration = CoreCaseDataConfiguration.class)
public interface CoreCaseDataApi {

    String SERVICE_AUTHORIZATION = "ServiceAuthorization";

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
                    + "{eventId}/token",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    StartEventResponse startForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
            @PathVariable String eventId
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    CaseDetails submitForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
            @RequestParam("ignore-warning") boolean ignoreWarning,
            @RequestBody CaseDataContent caseDataContent
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    List<CaseDetails> searchForCaseworker(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
            @QueryMap Map<String, Object> searchCriteria
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
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
            @PathVariable String eventId
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/citizens/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    CaseDetails submitForCitizen(
            @RequestHeader(AUTHORIZATION) String authorisation,
            @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorisation,
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
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
            @PathVariable String userId,
            @PathVariable String jurisdictionId,
            @PathVariable String caseType,
            @RequestParam Map<String, Object> searchCriteria
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/status/health",
            headers = CONTENT_TYPE + "=" + APPLICATION_JSON_UTF8_VALUE
    )
    InternalHealth health();
}
