package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Data
@Builder
@Jacksonized
public class CaseAssignmentUserRolesWithOrganisationRequest {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserRoleWithOrganisation> caseAssignmentUserRolesWithOrganisation;

}
