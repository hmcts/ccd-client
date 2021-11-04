package uk.gov.hmcts.reform.ccd.client.model.metadatafields;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition.AccessControlList;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition.FieldTypeDefinition;

import java.util.List;

@Getter
@Setter
@ToString
public class CaseViewField {

    private String id;
    private String label;
    @JsonProperty("hint_text")
    private String hintText;
    @JsonProperty("field_type")
    private FieldTypeDefinition fieldTypeDefinition;
    private Boolean hidden;
    @JsonProperty("validation_expr")
    private String validationExpression;
    @JsonProperty("security_label")
    private String securityLabel;
    @JsonProperty("order")
    private Integer order;
    private Object value;
    @JsonProperty("formatted_value")
    private Object formattedValue;
    @JsonProperty("display_context")
    private String displayContext;
    @JsonProperty("display_context_parameter")
    private String displayContextParameter;
    @JsonProperty("show_condition")
    private String showCondition;
    @JsonProperty("show_summary_change_option")
    private Boolean showSummaryChangeOption;
    @JsonProperty("show_summary_content_option")
    private Integer showSummaryContentOption;
    @JsonProperty("retain_hidden_value")
    private Boolean retainHiddenValue;
    @JsonProperty("publish")
    private Boolean publish;
    @JsonProperty("publish_as")
    private String publishAs;
    @JsonProperty("acls")
    private List<AccessControlList> accessControlLists;
    private boolean metadata;

}
