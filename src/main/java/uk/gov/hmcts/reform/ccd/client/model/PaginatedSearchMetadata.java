package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginatedSearchMetadata {

    @JsonProperty("total_results_count")
    private Integer totalResultsCount;

    @JsonProperty("total_pages_count")
    private Integer totalPagesCount;
}