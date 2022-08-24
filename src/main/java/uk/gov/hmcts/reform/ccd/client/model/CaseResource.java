package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CaseResource {

    @JsonProperty("id")
    private String reference;

    @JsonProperty("jurisdiction")
    private String jurisdiction;

    @JsonProperty("case_type")
    private String caseType;

    @JsonProperty("created_on")
    private LocalDateTime createdOn;

    @JsonProperty("last_modified_on")
    private LocalDateTime lastModifiedOn;

    @JsonProperty("last_state_modified_on")
    private LocalDateTime lastStateModifiedOn;

    @JsonProperty("state")
    private String state;

    @JsonProperty("security_classification")
    private Classification securityClassification;

    @JsonProperty("data")
    private Map<String, JsonNode> data;

    @JsonProperty("data_classification")
    private Map<String, JsonNode> dataClassification;

    @JsonProperty("after_submit_callback_response")
    @SuppressWarnings("squid:common-java:DuplicatedBlocks")
    private SubmittedCallbackResponse afterSubmitCallbackResponse;

    @JsonProperty("callback_response_status_code")
    @SuppressWarnings("squid:common-java:DuplicatedBlocks")
    private Integer callbackResponseStatusCode;

    @JsonProperty("callback_response_status")
    @SuppressWarnings("squid:common-java:DuplicatedBlocks")
    private String callbackResponseStatus;

    @JsonProperty("delete_draft_response_status_code")
    @SuppressWarnings("squid:common-java:DuplicatedBlocks")
    private Integer deleteDraftResponseStatusCode;

    @JsonProperty("delete_draft_response_status")
    @SuppressWarnings("squid:common-java:DuplicatedBlocks")
    private String deleteDraftResponseStatus;
}
