package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {
    @JsonProperty("document_url")
    String documentURL;
    @JsonProperty("document_filename")
    String documentFilename;
    @JsonProperty("document_binary_url")
    String documentBinaryURL;
    @JsonProperty("attribute_path")
    String attributePath;
    @JsonProperty("upload_timestamp")
    LocalDateTime uploadTimestamp;
}
