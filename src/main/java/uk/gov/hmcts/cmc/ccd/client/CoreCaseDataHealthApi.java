package uk.gov.hmcts.cmc.ccd.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.gov.hmcts.cmc.ccd.client.healthcheck.InternalHealth;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "core-case-data-api", url = "${core_case_data.api.url}")
public interface CoreCaseDataHealthApi {

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/health",
        headers = CONTENT_TYPE + "=" + APPLICATION_JSON_UTF8_VALUE
    )
    InternalHealth health();

}
