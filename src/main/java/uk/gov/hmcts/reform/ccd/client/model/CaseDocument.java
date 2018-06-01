package uk.gov.hmcts.reform.ccd.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * A print callback takes a List of CaseDocument's as the response
 */
@Data
@Builder
public class CaseDocument {
    private String url;
    private String name;
    private String type;
    private String description;
}
