package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = SearchResult.SearchResultBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    int total;
    List<CaseDetails> cases;
}
