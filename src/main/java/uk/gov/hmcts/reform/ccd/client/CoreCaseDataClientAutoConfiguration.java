package uk.gov.hmcts.reform.ccd.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.ccd.client.healthcheck.CoreCaseDataHealthIndicator;

@Configuration
@ConditionalOnProperty(prefix = "core_case_data", name = "api.url")
@EnableFeignClients(basePackages = {"uk.gov.hmcts.reform.ccd.client"})
public class CoreCaseDataClientAutoConfiguration {

    @Bean
    public CoreCaseDataHealthIndicator coreCaseData(CoreCaseDataApi coreCaseDataApi) {
        return new CoreCaseDataHealthIndicator(coreCaseDataApi);
    }

}
