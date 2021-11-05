package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonDeserialize(builder = StartEventResponse.StartEventResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartEventResponse {

    @JsonProperty("case_details")
    private CaseDetails caseDetails;

    @JsonProperty("event_id")
    private String eventId;

    private String token;
}
