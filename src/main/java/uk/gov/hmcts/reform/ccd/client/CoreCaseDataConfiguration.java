package uk.gov.hmcts.reform.ccd.client;

import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;
import uk.gov.hmcts.reform.ccd.client.model.SearchCriteria;

class CoreCaseDataConfiguration {

    @Bean
    SearchCriteria searchCriteria(ObjectMapper objectMapper) {
        return new SearchCriteria(objectMapper);
    }
}
