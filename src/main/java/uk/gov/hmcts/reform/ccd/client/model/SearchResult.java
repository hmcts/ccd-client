package uk.gov.hmcts.reform.ccd.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResult {
    int total;
    List<CaseDetails> cases;
}
