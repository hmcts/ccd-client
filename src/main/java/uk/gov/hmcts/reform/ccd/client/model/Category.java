package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @JsonProperty("category_id")
    String categoryId;
    @JsonProperty("category_name")
    String categoryName;
    @JsonProperty("category_order")
    Integer categoryOrder;
    @JsonProperty("documents")
    List<Document> documents;
    @JsonProperty("sub_categories")
    List<Category> subCategories;
}
