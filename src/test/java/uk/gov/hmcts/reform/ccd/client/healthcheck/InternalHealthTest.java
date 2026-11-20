package uk.gov.hmcts.reform.ccd.client.healthcheck;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Status;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

class InternalHealthTest {

    private static final String ACTUATOR_HEALTH_JSON = """
        {"status":"UP","components":{"diskSpace":{"status":"UP"}}}
        """;

    @Test
    @DisplayName("deserializes actuator health JSON with Jackson 3 defaults")
    void deserializesWithJackson3Defaults() {
        JsonMapper mapper = JsonMapper.builder().build();

        InternalHealth health = mapper.readValue(ACTUATOR_HEALTH_JSON, InternalHealth.class);

        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    @DisplayName("deserializes actuator health JSON when consumer enables spring.jackson.use-jackson2-defaults")
    void deserializesWithJackson2Defaults() {
        // Boot 4 consumers often set spring.jackson.use-jackson2-defaults=true, which calls
        // configureForJackson2(). Without @JsonProperty on the @JsonCreator arg, object JSON fails.
        JsonMapper mapper = JsonMapper.builder()
            .configureForJackson2()
            .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS, DateTimeFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
            .build();

        InternalHealth health = mapper.readValue(ACTUATOR_HEALTH_JSON, InternalHealth.class);

        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }
}
