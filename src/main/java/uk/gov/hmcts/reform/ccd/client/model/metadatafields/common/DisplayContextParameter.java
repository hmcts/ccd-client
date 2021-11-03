package uk.gov.hmcts.reform.ccd.client.model.metadatafields.common;

public class DisplayContextParameter {

    private static final String MULTIPLE_PARAMETERS_STRING = "),";

    private DisplayContextParameterType type;

    private String value;

    public DisplayContextParameter(DisplayContextParameterType type, String value) {
        this.type = type;
        this.value = value;
    }

    public DisplayContextParameterType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
