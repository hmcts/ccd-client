package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.common.CommonField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
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
}
