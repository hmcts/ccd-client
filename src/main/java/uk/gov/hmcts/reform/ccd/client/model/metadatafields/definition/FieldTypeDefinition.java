package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public List<FixedListItemDefinition> getFixedListItemDefinitions() {
        return fixedListItemDefinitions;
    }

    public void setFixedListItemDefinitions(List<FixedListItemDefinition> fixedListItemDefinitions) {
        this.fixedListItemDefinitions = fixedListItemDefinitions;
    }

    public List<CaseFieldDefinition> getComplexFields() {
        return complexFields;
    }

    public void setComplexFields(List<CaseFieldDefinition> complexFields) {
        this.complexFields = complexFields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FieldTypeDefinition getCollectionFieldTypeDefinition() {
        return collectionFieldTypeDefinition;
    }

    public void setCollectionFieldTypeDefinition(FieldTypeDefinition collectionFieldTypeDefinition) {
        this.collectionFieldTypeDefinition = collectionFieldTypeDefinition;
    }
}
