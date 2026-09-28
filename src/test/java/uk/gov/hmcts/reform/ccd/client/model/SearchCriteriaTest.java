package uk.gov.hmcts.reform.ccd.client.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

class SearchCriteriaTest {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    private final SearchCriteria searchCriteria = new SearchCriteria();

    @Test
    void matchAllQueryUsesDefaultSize() {
        assertThat(searchCriteria.matchAllQuery())
            .isEqualTo("{\"size\":10,\"query\":{\"match_all\":{}}}");
        assertThat(searchCriteria.matchAllQuery(null))
            .isEqualTo("{\"size\":10,\"query\":{\"match_all\":{}}}");
        assertThat(searchCriteria.matchAllQuery(50))
            .isEqualTo("{\"size\":50,\"query\":{\"match_all\":{}}}");
    }

    @Test
    void searchByQueryMatchesOnKeyAndValue() {
        assertThat(searchCriteria.searchByQuery("data.applicant1FirstName", "Jane"))
            .isEqualTo("{\"size\":10,\"query\":{\"bool\":{\"filter\":{\"match\":"
                + "{\"data.applicant1FirstName\":\"Jane\"}}}}}");
        assertThat(searchCriteria.searchByQuery("reference", null, 5))
            .isEqualTo("{\"size\":5,\"query\":{\"bool\":{\"filter\":{\"match\":{\"reference\":null}}}}}");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "plain", "quote\"d", "back\\slash", "new\nline", "tab\tand\rreturn", "\b\f", "\u0001\u001f",
        "unicode £ é 漢字 😀", "</script>", ""
    })
    void escapesValuesExactlyAsJacksonDid(String text) {
        String expected = JSON.createObjectNode()
            .put("size", 10)
            .set("query", JSON.createObjectNode()
                .set("bool", JSON.createObjectNode()
                    .set("filter", JSON.createObjectNode()
                        .set("match", JSON.createObjectNode().put(text, text)))))
            .toString();

        String actual = searchCriteria.searchByQuery(text, text);

        assertThat(actual).isEqualTo(expected);
        assertThat(JSON.readTree(actual).at("/query/bool/filter/match").get(text).asString()).isEqualTo(text);
    }
}
