package uk.gov.hmcts.reform.ccd.client.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;

public class CoreCaseDataHealthIndicator implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreCaseDataHealthIndicator.class);

    private final CoreCaseDataApi coreCaseDataApi;

    public CoreCaseDataHealthIndicator(final CoreCaseDataApi coreCaseDataApi) {
        this.coreCaseDataApi = coreCaseDataApi;
    }

    @Override
    public Health health() {
        try {
            InternalHealth internalHealth = this.coreCaseDataApi.health();
            return new Health.Builder(internalHealth.getStatus()).build();
        } catch (Exception ex) {
            LOGGER.error("Error on core case data store app healthcheck", ex);
            return Health.down(ex).build();
        }
    }

}
