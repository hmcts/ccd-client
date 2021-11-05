package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Jacksonized
public class SearchResult {
    int total;
    List<CaseDetails> cases;
}
