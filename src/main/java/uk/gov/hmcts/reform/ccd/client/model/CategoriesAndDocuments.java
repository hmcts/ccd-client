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
public class CategoriesAndDocuments {
    @JsonProperty("case_version")
    Integer caseVersion;
    @JsonProperty("categories")
    List<Category> categories;
    @JsonProperty("uncategorised_documents")
    List<Document> uncategorisedDocuments;
}
