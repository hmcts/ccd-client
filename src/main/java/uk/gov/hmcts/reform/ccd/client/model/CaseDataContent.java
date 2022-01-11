package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class CaseDataContent {

    private Event event;

    private Object data;

    @JsonProperty("supplementary_data_request")
    private Map<String, Map<String, Object>> supplementaryDataRequest;

    @JsonProperty("security_classification")
    private Classification securityClassification;

    @JsonProperty("event_token")
    private String eventToken;

    @JsonProperty("ignore_warning")
    private boolean ignoreWarning;

    @JsonProperty("case_reference")
    private String caseReference;
}
