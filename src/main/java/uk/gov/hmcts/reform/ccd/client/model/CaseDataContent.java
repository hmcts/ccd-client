package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class CaseDataContent {

    private Event event;

    private Object data;

    @JsonProperty("security_classification")
    private Classification securityClassification;

    @JsonProperty("event_token")
    private String eventToken;

    @JsonProperty("ignore_warning")
    private boolean ignoreWarning;

    @JsonProperty("case_reference")
    private String caseReference;
}
