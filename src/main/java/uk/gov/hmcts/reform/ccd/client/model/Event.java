package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Event {
    private String id;
    private String summary;
    private String description;
}
