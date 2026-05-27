package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class CaseAssignmentUserRolesResource {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserRole> caseAssignmentUserRoles;
}
