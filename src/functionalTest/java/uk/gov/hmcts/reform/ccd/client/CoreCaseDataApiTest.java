package uk.gov.hmcts.reform.ccd.client;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.IdamTestApi;
import uk.gov.hmcts.reform.idam.client.models.test.CreateUserRequest;
import uk.gov.hmcts.reform.idam.client.models.test.UserGroup;
import uk.gov.hmcts.reform.idam.client.models.test.UserRole;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@EnableAutoConfiguration
@DisplayName("Core case data api")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfiguration.class})
public class CoreCaseDataApiTest {

    // can be found here: https://github.com/hmcts/ccd-definition-store-api/blob/master/aat/src/resource/CCD_CNP_27.xlsx
    private static final String JURISDICTION = "AUTOTEST1";
    private static final String CASE_TYPE = "AAT";
    private static final String CREATE_CASE_EVENT = "CREATE";

    @Autowired
    private CoreCaseDataApi coreCaseDataApi;

    @Autowired
    private IdamClient idamClient;

    @Autowired
    private IdamTestApi idamTestApi;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    private static String idamCaseWorkerToken;
    private static String idamCaseWorkerId;

    @BeforeEach
    void init() {
        String caseworkerEmail = "autocaseworker-test@hmcts.net";
        try {
            idamTestApi.createUser(CreateUserRequest.builder()
                    .email(caseworkerEmail)
                    .userGroup(new UserGroup("caseworker"))
                    .roles(singletonList(new UserRole("caseworker-autotest1")))
                    .build());
        } catch (FeignException e) {
            if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                log.info("Bad request from idam, probably user already exists, continuing");
            } else {
                throw e;
            }
        }

        idamCaseWorkerToken = idamClient.authenticateUser(caseworkerEmail, CreateUserRequest.DEFAULT_PASSWORD);
        idamCaseWorkerId = idamClient.getUserDetails(idamCaseWorkerToken).getId();
    }

    @Test
    @DisplayName("Should be able to create a case for a caseworker")
    void createCaseForCaseworker() {
        StartEventResponse startEventResponse = coreCaseDataApi.startForCaseworker(
                idamCaseWorkerToken,
                authTokenGenerator.generate(),
                idamCaseWorkerId,
                JURISDICTION,
                CASE_TYPE,
                CREATE_CASE_EVENT
        );

        Map<String, String> caseData = new HashMap<String, String>() {
            {
                put("TextField", "text...");
            }
        };

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder().id(startEventResponse.getEventId()).build())
                .data(caseData)
                .build();

        CaseDetails caseDetails = coreCaseDataApi.submitForCaseworker(
                idamCaseWorkerToken,
                authTokenGenerator.generate(),
                idamCaseWorkerId,
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );

        assertThat(caseDetails.getData().get("TextField"))
                .isEqualTo("text...");
    }
}
