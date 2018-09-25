package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CallbackRequest {

    @JsonProperty("case_details")
    private Map<String, Object> caseDetails;

    @JsonProperty("case_details_before")
    private Map<String, Object> caseDetailsBefore;

    @JsonProperty("event_id")
    private String eventId;
}
