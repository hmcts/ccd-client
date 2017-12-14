package uk.gov.hmcts.reform.ccd.client;

import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "core-case-data-api", url = "${core_case_data.api.url}",
    configuration = CoreCaseDataApi.CoreCaseDataConfiguration.class)
public interface CoreCaseDataApi {

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/event-triggers/"
            + "{eventId}/token",
        headers = CONTENT_TYPE + "=" + APPLICATION_JSON_VALUE
    )
    ResponseEntity<StartEventResponse> start(@RequestHeader(AUTHORIZATION) String authorisation,
                                             @RequestHeader("ServiceAuthorization") String serviceAuthorization,
                                             @PathVariable String userId,
                                             @PathVariable String jurisdictionId,
                                             @PathVariable String caseType,
                                             @PathVariable String eventId
    );

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/caseworkers/{userId}/jurisdictions/{jurisdictionId}/case-types/{caseType}/cases"
    )
    ResponseEntity<CaseDetails> submit(@RequestHeader(AUTHORIZATION) String authorisation,
                                       @RequestHeader("ServiceAuthorization") String serviceAuthorisation,
                                       @PathVariable String userId,
                                       @PathVariable String jurisdictionId,
                                       @PathVariable String caseType,
                                       @RequestParam("ignore-warning") boolean ignoreWarning,
                                       @RequestBody CaseDataContent caseDataContent
    );

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/status/health",
        headers = CONTENT_TYPE + "=" + APPLICATION_JSON_UTF8_VALUE
    )
    InternalHealth health();

    class CoreCaseDataConfiguration {
        @Bean
        @Primary
        @Scope("prototype")
        Decoder feignDecoder() {
            return new JacksonDecoder();
        }
    }
}
