package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class SearchCriteria {
    private static final Integer MINIMUM_SIZE_PER_PAGES = 10;

    private final ObjectMapper mapper;

    public SearchCriteria(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String matchAllQuery() {
        return matchAllQuery(MINIMUM_SIZE_PER_PAGES);
    }

    public String matchAllQuery(Integer size) {
        JsonNode query = mapper.createObjectNode()
            .put("size", Optional.ofNullable(size).orElse(MINIMUM_SIZE_PER_PAGES))
            .set("query", mapper.createObjectNode()
                .set("match_all", mapper.createObjectNode()));

        return query.toString();
    }

    public String searchByQuery(String key, String value) {
        return searchByQuery(key, value, MINIMUM_SIZE_PER_PAGES);
    }

    public String searchByQuery(String key, String value, Integer size) {
        JsonNode query = mapper.createObjectNode()
            .put("size", Optional.ofNullable(size).orElse(MINIMUM_SIZE_PER_PAGES))
            .set("query", mapper.createObjectNode()
                .set("bool", mapper.createObjectNode()
                    .set("filter", mapper.createObjectNode()
                        .set("match", mapper.createObjectNode()
                            .put(key, value)
                        )
                    )
                )
            );

        return query.toString();
    }
}
