package uk.gov.hmcts.reform.ccd.client.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CaseAssignmentUserRolesResponse {
    private String status;
}
