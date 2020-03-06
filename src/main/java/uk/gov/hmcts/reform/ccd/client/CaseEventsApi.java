package uk.gov.hmcts.reform.ccd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.gov.hmcts.reform.ccd.client.model.CaseEventDetail;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi.SERVICE_AUTHORIZATION;

@FeignClient(
    name = "ccd-events-api",
    url = "${core_case_data.api.url}",
    configuration = CoreCaseDataConfiguration.class
)
public interface CaseEventsApi {

    @GetMapping(
        value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases/{caseId}/events",
        headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    List<CaseEventDetail> findEventDetailsForCase(
        @RequestHeader(AUTHORIZATION) String authorisation,
        @RequestHeader(SERVICE_AUTHORIZATION) String serviceAuthorization,
        @PathVariable("userId") String userId,
        @PathVariable("jurisdictionId") String jurisdictionId,
        @PathVariable("caseType") String caseType,
        @PathVariable("caseId") String caseId
    );
}
