package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComplexACL extends AccessControlList {
    private String listElementCode;
}
