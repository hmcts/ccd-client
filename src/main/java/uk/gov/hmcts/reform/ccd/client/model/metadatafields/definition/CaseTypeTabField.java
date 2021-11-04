package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CaseTypeTabField implements Serializable {


    @JsonProperty("case_field")
    private CaseFieldDefinition caseFieldDefinition = null;

    @JsonProperty("order")
    private Integer displayOrder = null;

    @JsonProperty("show_condition")
    private String showCondition = null;

    @JsonProperty("display_context_parameter")
    private String displayContextParameter;
}
