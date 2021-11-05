package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartEventResponse {

    @JsonProperty("case_details")
    private CaseDetails caseDetails;

    @JsonProperty("event_id")
    private String eventId;

    private String token;
}
