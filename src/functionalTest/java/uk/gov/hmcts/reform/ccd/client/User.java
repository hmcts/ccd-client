package uk.gov.hmcts.reform.ccd.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.hmcts.reform.idam.client.models.UserInfo;

@Getter
@AllArgsConstructor
class User {
    private String authToken;
    private UserInfo userDetails;
}
