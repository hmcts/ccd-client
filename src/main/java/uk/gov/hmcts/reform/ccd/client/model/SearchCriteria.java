package uk.gov.hmcts.reform.ccd.client.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Builds Elasticsearch query strings for the CCD search API.
 *
 * <p>Writes JSON directly rather than through an ObjectMapper so the client works with either
 * Jackson 2 or Jackson 3 on the consumer's classpath.
 */
public class SearchCriteria {
    private static final Integer MINIMUM_SIZE_PER_PAGES = 10;

    public String matchAllQuery() {
        return matchAllQuery(MINIMUM_SIZE_PER_PAGES);
    }

    public String matchAllQuery(Integer size) {
        return "{\"size\":" + sizeOrDefault(size) + ",\"query\":{\"match_all\":{}}}";
    }

    public String searchByQuery(String key, String value) {
        return searchByQuery(key, value, MINIMUM_SIZE_PER_PAGES);
    }

    public String searchByQuery(String key, String value, Integer size) {
        return "{\"size\":" + sizeOrDefault(size)
            + ",\"query\":{\"bool\":{\"filter\":{\"match\":{"
            + quote(Objects.requireNonNull(key, "key")) + ":" + (value == null ? "null" : quote(value))
            + "}}}}}";
    }

    private static int sizeOrDefault(Integer size) {
        return Optional.ofNullable(size).orElse(MINIMUM_SIZE_PER_PAGES);
    }

    private static String quote(String text) {
        StringBuilder json = new StringBuilder(text.length() + 2).append('"');
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> {
                    if (c < 0x20) {
                        json.append(String.format("\\u%04X", (int) c));
                    } else {
                        json.append(c);
                    }
                }
            }
        }
        return json.append('"').toString();
    }
}
