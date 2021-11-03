package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.ToString;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.common.CommonField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@ApiModel(description = "")
public class CaseFieldDefinition implements Serializable, CommonField {

    private static final long serialVersionUID = -4257574164546267919L;

    private String id = null;
    @JsonProperty("case_type_id")
    private String caseTypeId = null;
    private String label = null;
    @JsonProperty("hint_text")
    private String hintText = null;
    @JsonProperty("field_type")
    private FieldTypeDefinition fieldTypeDefinition = null;
    private Boolean hidden = null;
    @JsonProperty("security_classification")
    private String securityLabel = null;
    @JsonProperty("live_from")
    private String liveFrom = null;
    @JsonProperty("live_until")
    private String liveUntil = null;
    private Integer order;
    @JsonProperty("show_condition")
    private String showConditon = null;
    @JsonProperty("acls")
    private List<AccessControlList> accessControlLists;
    @JsonProperty("complexACLs")
    private List<ComplexACL> complexACLs = new ArrayList<>();
    private boolean metadata;
    @JsonProperty("display_context")
    private String displayContext;
    @JsonProperty("display_context_parameter")
    private String displayContextParameter;
    @JsonProperty("retain_hidden_value")
    private Boolean retainHiddenValue;
    @JsonProperty("formatted_value")
    private Object formattedValue;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(String caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    @Override
    public FieldTypeDefinition getFieldTypeDefinition() {
        return fieldTypeDefinition;
    }

    public void setFieldTypeDefinition(FieldTypeDefinition fieldTypeDefinition) {
        this.fieldTypeDefinition = fieldTypeDefinition;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getSecurityLabel() {
        return securityLabel;
    }

    public void setSecurityLabel(String securityLabel) {
        this.securityLabel = securityLabel;
    }

    public String getLiveFrom() {
        return liveFrom;
    }

    public void setLiveFrom(String liveFrom) {
        this.liveFrom = liveFrom;
    }

    public String getLiveUntil() {
        return liveUntil;
    }

    public void setLiveUntil(String liveUntil) {
        this.liveUntil = liveUntil;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public String getShowConditon() {
        return showConditon;
    }

    public void setShowConditon(String showConditon) {
        this.showConditon = showConditon;
    }

    @Override
    public List<AccessControlList> getAccessControlLists() {
        return accessControlLists;
    }

    public void setAccessControlLists(List<AccessControlList> accessControlLists) {
        this.accessControlLists = accessControlLists;
    }

    public List<ComplexACL> getComplexACLs() {
        return complexACLs;
    }

    public void setComplexACLs(List<ComplexACL> complexACLs) {
        this.complexACLs = complexACLs;
    }

    public boolean isMetadata() {
        return metadata;
    }

    public void setMetadata(boolean metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getDisplayContext() {
        return displayContext;
    }

    @Override
    public void setDisplayContext(String displayContext) {
        this.displayContext = displayContext;
    }

    @Override
    public String getDisplayContextParameter() {
        return displayContextParameter;
    }

    @Override
    public void setDisplayContextParameter(String displayContextParameter) {
        this.displayContextParameter = displayContextParameter;
    }

    public Boolean getRetainHiddenValue() {
        return retainHiddenValue;
    }

    public void setRetainHiddenValue(Boolean retainHiddenValue) {
        this.retainHiddenValue = retainHiddenValue;
    }

    @Override
    public Object getFormattedValue() {
        return formattedValue;
    }

    @Override
    public void setFormattedValue(Object formattedValue) {
        this.formattedValue = formattedValue;
    }

}
