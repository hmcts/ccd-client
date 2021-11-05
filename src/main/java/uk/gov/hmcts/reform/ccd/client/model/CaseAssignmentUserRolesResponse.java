package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class CaseAssignmentUserRolesResponse {
    @JsonProperty("status_message")
    private String statusMessage;
}
