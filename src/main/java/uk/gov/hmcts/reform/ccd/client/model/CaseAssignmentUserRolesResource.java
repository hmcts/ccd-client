package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder(toBuilder = true)
@Jacksonized
@AllArgsConstructor
public class CaseAssignmentUserRolesResource {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserRole> caseAssignmentUserRoles;
}
