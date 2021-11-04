package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class FixedListItemDefinition implements Serializable {

    private static final long serialVersionUID = 6196146295016140921L;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("label")
    private String label = null;

    @JsonProperty("order")
    private String order = null;
}
