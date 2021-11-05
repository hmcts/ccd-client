package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class Event {
    private String id;
    private String summary;
    private String description;
}
