package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.CaseViewField;

import java.util.List;

@Data
@NoArgsConstructor
public class GetCaseCallbackResponse {

    @JsonProperty("metadataFields")
    private List<CaseViewField> metadataFields;
}