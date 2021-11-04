package uk.gov.hmcts.reform.ccd.client.model.metadatafields.common;

import uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition.AccessControlList;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition.DisplayContext;
import uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition.FieldTypeDefinition;

import java.util.List;
import java.util.Optional;

public interface CommonField {

    FieldTypeDefinition getFieldTypeDefinition();

    String getId();

    List<AccessControlList> getAccessControlLists();

    String getDisplayContext();

    void setDisplayContext(String displayContext);

    void setDisplayContextParameter(String displayContextParameter);

    Object getFormattedValue();

    void setFormattedValue(Object formattedValue);


    default DisplayContext displayContextType() {
        return Optional.ofNullable(getDisplayContext())
                .filter(dc -> !dc.equals("HIDDEN"))
                .map(DisplayContext::valueOf)
                .orElse(null);
    }
}
