package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@JsonDeserialize(builder = CaseDetails.CaseDetailsBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDetails {

    private Long id;

    private String jurisdiction;

    @JsonProperty("case_type_id")
    @JsonAlias("case_type")
    private String caseTypeId;

    @JsonProperty("created_date")
    @JsonAlias("created_on")
    private LocalDateTime createdDate;

    @JsonProperty("last_modified")
    @JsonAlias("last_modified_on")
    private LocalDateTime lastModified;

    private String state;

    @JsonProperty("locked_by_user_id")
    private Integer lockedBy;

    @JsonProperty("security_level")
    private Integer securityLevel;

    @JsonProperty("case_data")
    @JsonAlias("data")
    private Map<String, Object> data;

    @JsonProperty("security_classification")
    private Classification securityClassification;

    @JsonProperty("callback_response_status")
    private String callbackResponseStatus;
}
