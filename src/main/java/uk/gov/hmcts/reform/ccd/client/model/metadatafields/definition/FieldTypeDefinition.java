package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class FieldTypeDefinition implements Serializable {
    
    private String id = null;
    private String type = null;
    private BigDecimal min = null;
    private BigDecimal max = null;
    @JsonProperty("regular_expression")
    private String regularExpression = null;
    @JsonProperty("fixed_list_items")
    private List<FixedListItemDefinition> fixedListItemDefinitions = new ArrayList<>();
    @JsonProperty("complex_fields")
    private List<CaseFieldDefinition> complexFields = new ArrayList<>();
    @JsonProperty("collection_field_type")
    private FieldTypeDefinition collectionFieldTypeDefinition = null;
}
