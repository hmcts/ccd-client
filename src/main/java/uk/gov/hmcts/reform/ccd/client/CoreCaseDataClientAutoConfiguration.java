package uk.gov.hmcts.reform.ccd.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.ccd.client.healthcheck.CoreCaseDataHealthIndicator;

@Configuration
@EnableFeignClients(basePackages = "uk.gov.hmcts.reform.ccd.client")
public class CoreCaseDataClientAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "core_case_data", name = "api.url")
    public CoreCaseDataHealthIndicator healthIndicator(CoreCaseDataApi coreCaseDataApi) {
        return new CoreCaseDataHealthIndicator(coreCaseDataApi);
    }
}
