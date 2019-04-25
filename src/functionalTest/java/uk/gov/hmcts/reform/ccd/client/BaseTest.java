package uk.gov.hmcts.reform.ccd.client;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;
import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.IdamTestApi;
import uk.gov.hmcts.reform.idam.client.models.UserDetails;
import uk.gov.hmcts.reform.idam.client.models.test.CreateUserRequest;
import uk.gov.hmcts.reform.idam.client.models.test.UserGroup;
import uk.gov.hmcts.reform.idam.client.models.test.UserRole;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

@Slf4j
@SpringBootTest(classes = {TestConfiguration.class})
public class BaseTest {

    // can be found here: https://github.com/hmcts/ccd-definition-store-api/blob/master/aat/src/resource/CCD_CNP_27.xlsx
    static final String JURISDICTION = "AUTOTEST1";
    static final String CASE_TYPE = "AAT";
    static final String UPDATE_CASE_EVENT = "START_PROGRESS";
    private static final String CREATE_CASE_EVENT = "CREATE";
    private static final String CASE_REFERENCE = "REF123";

    private static final Map<String, String> CREATE_CASE_DATA = new HashMap<String, String>() {
        {
            put("TextField", "text...");
        }
    };

    @Autowired
    protected IdamClient idamClient;

    @Autowired
    protected IdamTestApi idamTestApi;

    @Autowired
    protected CoreCaseDataApi coreCaseDataApi;

    @Autowired
    protected AuthTokenGenerator authTokenGenerator;

    User createCitizen() {
        String citizenEmail = "autocitizen-ccdclient@hmcts.net";
        try {
            idamTestApi.createUser(CreateUserRequest.builder()
                    .email(citizenEmail)
                    .userGroup(new UserGroup("citizens"))
                    .build());
        } catch (FeignException e) {
            if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                log.info("Bad request from idam, probably user already exists, continuing");
            } else {
                throw e;
            }
        }

        String token = idamClient.authenticateUser(citizenEmail, CreateUserRequest.DEFAULT_PASSWORD);
        UserDetails userDetails = idamClient.getUserDetails(token);

        return new User(token, userDetails);
    }

    User createCaseworker() {
        return createCaseworker("autocaseworker-ccdclient@hmcts.net");
    }

    User createCaseworker(String email) {
        try {
            idamTestApi.createUser(CreateUserRequest.builder()
                    .email(email)
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

        String token = idamClient.authenticateUser(email, CreateUserRequest.DEFAULT_PASSWORD);
        UserDetails userDetails = idamClient.getUserDetails(token);

        return new User(token, userDetails);
    }

    CaseDetails createCaseForCaseworker(User caseworker) {
        StartEventResponse startEventResponse = coreCaseDataApi.startForCaseworker(
                caseworker.getAuthToken(),
                authTokenGenerator.generate(),
                caseworker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                CREATE_CASE_EVENT
        );

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder().id(startEventResponse.getEventId()).build())
                .data(CREATE_CASE_DATA)
                .caseReference(CASE_REFERENCE)
                .build();

        return coreCaseDataApi.submitForCaseworker(
                caseworker.getAuthToken(),
                authTokenGenerator.generate(),
                caseworker.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );
    }

    CaseDetails createCase(User user) {
        StartEventResponse startEventResponse = coreCaseDataApi.startCase(
                user.getAuthToken(),
                authTokenGenerator.generate(),
                CASE_TYPE,
                CREATE_CASE_EVENT
        );

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder().id(startEventResponse.getEventId()).build())
                .data(CREATE_CASE_DATA)
                .build();

        return coreCaseDataApi.submitForCaseworker(
                user.getAuthToken(),
                authTokenGenerator.generate(),
                user.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );
    }

    CaseDetails createCaseForCitizen(User citizen) {
        StartEventResponse startEventResponse = coreCaseDataApi.startForCitizen(
                citizen.getAuthToken(),
                authTokenGenerator.generate(),
                citizen.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                CREATE_CASE_EVENT
        );

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder().id(startEventResponse.getEventId()).build())
                .data(CREATE_CASE_DATA)
                .build();

        return coreCaseDataApi.submitForCitizen(
                citizen.getAuthToken(),
                authTokenGenerator.generate(),
                citizen.getUserDetails().getId(),
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );
    }
}
