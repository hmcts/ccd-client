package uk.gov.hmcts.reform.ccd.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import uk.gov.hmcts.reform.ccd.client.model.SearchCriteria;

class CoreCaseDataConfiguration {
    @Bean
    @Primary
    Decoder feignDecoder(ObjectMapper objectMapper) {
        return new JacksonDecoder(objectMapper);
    }

    @Bean
    SearchCriteria searchCriteria(ObjectMapper objectMapper) {
        return new SearchCriteria(objectMapper);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnUnknownProperties(false);
        return builder.build();
    }
}
