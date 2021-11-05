package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class EventRequestData {
    private String userToken;
    private String userId;
    private String jurisdictionId;
    private String caseTypeId;
    private String eventId;
    private boolean ignoreWarning;
}
