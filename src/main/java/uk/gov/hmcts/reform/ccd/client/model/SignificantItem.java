package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class SignificantItem {
    private String type;
    private String description;
    private String url;
}
