package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@ApiModel(description = "")
public class FixedListItemDefinition implements Serializable {

    private static final long serialVersionUID = 6196146295016140921L;

    @ApiModelProperty(value = "Code")
    @JsonProperty("code")
    private String code = null;

    @JsonProperty("label")
    private String label = null;

    @ApiModelProperty(value = "")
    @JsonProperty("order")
    private String order = null;
}
