package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class CaseUser {

    public CaseUser(@JsonProperty("user_id") String userId, @JsonProperty("case_roles")  Set<String> caseRoles) {
        this.userId = userId;
        this.caseRoles = caseRoles;
    }

    @JsonProperty("user_id")
    private String userId;

    @Builder.Default
    @JsonProperty("case_roles")
    private Set<String> caseRoles = new HashSet<>();
}
