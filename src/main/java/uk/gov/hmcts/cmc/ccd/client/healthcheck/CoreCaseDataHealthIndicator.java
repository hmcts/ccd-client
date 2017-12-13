package uk.gov.hmcts.cmc.ccd.client.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.cmc.ccd.client.CoreCaseDataHealthApi;

@Component
public class CoreCaseDataHealthIndicator implements HealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreCaseDataHealthIndicator.class);

    private final CoreCaseDataHealthApi coreCaseDataHealthApi;

    @Autowired
    public CoreCaseDataHealthIndicator(final CoreCaseDataHealthApi coreCaseDataHealthApi) {
        this.coreCaseDataHealthApi = coreCaseDataHealthApi;
    }

    @Override
    public Health health() {
        try {
            InternalHealth internalHealth = this.coreCaseDataHealthApi.health();
            return new Health.Builder(internalHealth.getStatus())
                .build();
        } catch (Exception ex) {
            LOGGER.error("Error on core case data store app healthcheck", ex);
            return Health.down(ex)
                .build();
        }
    }
}
