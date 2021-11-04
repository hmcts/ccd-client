package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel(description = "")
public class CaseTypeTabField implements Serializable {

    @ApiModelProperty(value = "")
    @JsonProperty("case_field")
    private CaseFieldDefinition caseFieldDefinition = null;

    @ApiModelProperty(value = "")
    @JsonProperty("order")
    private Integer displayOrder = null;

    @ApiModelProperty(value = "")
    @JsonProperty("show_condition")
    private String showCondition = null;

    @ApiModelProperty(value = "")
    @JsonProperty("display_context_parameter")
    private String displayContextParameter;
}
