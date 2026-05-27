package uk.gov.hmcts.reform.ccd.client;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;
import uk.gov.hmcts.reform.ccd.client.model.SearchCriteria;

@AutoConfiguration
public class CoreCaseDataConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SearchCriteria searchCriteria(ObjectMapper objectMapper) {
        return new SearchCriteria(objectMapper);
    }
}
