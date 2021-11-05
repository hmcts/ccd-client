package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseAssignmentUserRolesRequest {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserRoleWithOrganisation> caseAssignmentUserRolesWithOrganisation;

}
