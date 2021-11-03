package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "")
public class FixedListItemDefinition implements Serializable {

    private static final long serialVersionUID = 6196146295016140921L;
    private String code = null;
    private String label = null;
    private String order = null;

    @ApiModelProperty(value = "")
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("order")
    public String getOrder() {
        return order;
    }

    public void setOrder(final String order) {
        this.order = order;
    }
}
