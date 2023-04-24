package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Category {
    String categoryId;
    String categoryName;
    Integer categoryOrder;
    List<Document> documents;
    List<Category> subCategories;
}
