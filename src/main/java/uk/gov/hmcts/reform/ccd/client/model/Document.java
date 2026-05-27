package uk.gov.hmcts.reform.ccd.client.model;


import lombok.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Document {
    String documentURL;
    String documentFilename;
    String documentBinaryURL;
    String attributePath;
    LocalDateTime uploadTimestamp;
}
