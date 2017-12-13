package uk.gov.hmcts.cmc.ccd.client.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class CoreCaseDataHealthIndicator implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreCaseDataHealthIndicator.class);

    private final RestTemplate restTemplate;
    private final String coreCaseDataStoreBaseUrl;

    @Autowired
    public CoreCaseDataHealthIndicator
        (
            final RestTemplate restTemplate,
            @Value("${core_case_data.api.url}") String coreCaseDataStoreBaseUrl
        ) {
        this.restTemplate = restTemplate;
        this.coreCaseDataStoreBaseUrl = coreCaseDataStoreBaseUrl;
    }

    @Override
    public Health health() {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

            HttpEntity<?> entity = new HttpEntity<Object>("", httpHeaders);

            ResponseEntity<InternalHealth> exchange = restTemplate.exchange(
                coreCaseDataStoreBaseUrl + "/health",
                HttpMethod.GET,
                entity,
                InternalHealth.class);

            InternalHealth body = exchange.getBody();

            return new Health.Builder(body.getStatus())
                .build();
        } catch (Exception ex) {
            LOGGER.error("Error on core case data store app healthcheck", ex);
            return Health.down(ex)
                .build();
        }
    }
}
