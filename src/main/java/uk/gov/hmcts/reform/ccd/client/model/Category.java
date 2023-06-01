package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

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
