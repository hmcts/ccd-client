package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class SubmittedCallbackResponse implements CallbackResponse {

    @JsonProperty("confirmation_header")
    private String confirmationHeader;

    @JsonProperty("confirmation_body")
    private String confirmationBody;
}
