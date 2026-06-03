package uk.gov.hmcts.reform.ccd.client.model;

import lombok.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Category {
    String categoryId;
    String categoryName;
    Integer categoryOrder;
    List<Document> documents;
    List<Category> subCategories;
}
