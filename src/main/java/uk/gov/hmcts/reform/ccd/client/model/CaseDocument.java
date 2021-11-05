package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * A print callback takes a List of CaseDocument's as the response.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class CaseDocument {
    private String url;
    private String name;
    private String type;
    private String description;
}
