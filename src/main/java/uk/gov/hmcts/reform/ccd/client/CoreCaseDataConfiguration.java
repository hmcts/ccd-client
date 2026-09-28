package uk.gov.hmcts.reform.ccd.client;

import org.springframework.context.annotation.Bean;
import uk.gov.hmcts.reform.ccd.client.model.SearchCriteria;

class CoreCaseDataConfiguration {

    @Bean
    SearchCriteria searchCriteria() {
        return new SearchCriteria();
    }
}
