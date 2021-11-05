package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseEventDetail {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_last_name")
    private String userLastName;
    @JsonProperty("user_first_name")
    private String userFirstName;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("case_type_id")
    private String caseTypeId;
    @JsonProperty("case_type_version")
    private Integer caseTypeVersion;
    private String description;
    @JsonProperty("event_name")
    private String eventName;
    private String id;
    @JsonProperty("state_id")
    private String stateId;
    @JsonProperty("state_name")
    private String stateName;
    private Map<String, Object> data;
    private Map<String, Object> dataClassification;
    private SignificantItem significantItem;
}