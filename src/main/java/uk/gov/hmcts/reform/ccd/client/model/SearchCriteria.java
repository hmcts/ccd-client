package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchCriteria {

    private final ObjectMapper mapper;

    public SearchCriteria(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String matchAllQuery() {
        JsonNode query = mapper.createObjectNode()
            .put("size", 10)
            .set("query", mapper.createObjectNode()
                .set("match_all", mapper.createObjectNode()));

        return query.toString();
    }

    public String searchByQuery(String key, String value) {
        JsonNode query = mapper.createObjectNode()
            .put("size", 10)
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
