package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseEventDetails {
    private String userId;
    private String userLastName;
    private String userFirstName;
    private String eventName;
    private LocalDateTime createdDate;
    private String caseTypeId;
    private Integer caseTypeVersion;
    private String stateId;
    private String stateName;
    private Map<String, Object> data;
    private Map<String, Object> dataClassification;
    private SignificantItem significantItem;
}
