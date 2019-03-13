package uk.gov.hmcts.reform.ccd.client;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@EnableAutoConfiguration
@DisplayName("Core case data api")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfiguration.class})
// IntelliJ doesn't seem to pickup any of the clients here
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class CoreCaseDataApiTest {

    // can be found here: https://github.com/hmcts/ccd-definition-store-api/blob/master/aat/src/resource/CCD_CNP_27.xlsx
    private static final String JURISDICTION = "AUTOTEST1";
    private static final String CASE_TYPE = "AAT";
    private static final String CREATE_CASE_EVENT = "CREATE";
    private static final String UPDATE_CASE_EVENT = "START_PROGRESS";

    private static final Map<String, String> CREATE_CASE_DATA = new HashMap<String, String>() {
        {
            put("TextField", "text...");
        }
    };

    private static final Map<String, String> UPDATE_CASE_DATA = new HashMap<String, String>() {
        {
            put("TextField", "text updated");
        }
    };

    private static final Map<String, String> SEARCH_CRITERIA = new HashMap<String, String>() {
        {
            put("case.TextField", "text...");
        }
    };

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
    private static String idamCitizenToken;
    private static String idamCitizenId;

    @Nested
    @DisplayName("Case worker")
    class CaseWorker {
        @BeforeEach
        void init() {
            createCaseworker();
        }

        @Test
        @DisplayName("Should be able to retrieve a case")
        void getCaseForCaseworker() {
            CaseDetails caseDetails = createCaseForCaseworker();

            CaseDetails theCase = coreCaseDataApi
                    .getCase(idamCaseWorkerToken, authTokenGenerator.generate(), caseDetails.getId() + "");

            assertThat(theCase.getId())
                    .isEqualTo(caseDetails.getId());
        }
    }

    @Nested
    class V1 {

        @Nested
        @DisplayName("Case worker")
        class CaseWorker {

            @BeforeEach
            void init() {
                createCaseworker();
            }

            @Test
            @DisplayName("Should be able to create a case")
            void createCase() {
                CaseDetails caseDetails = createCaseForCaseworker();

                assertThat(caseDetails.getData().get("TextField"))
                        .isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to retrieve a case")
            void getCase() {
                CaseDetails caseDetails = createCaseForCaseworker();

                CaseDetails theCase = coreCaseDataApi
                        .readForCaseWorker(
                                idamCaseWorkerToken,
                                authTokenGenerator.generate(),
                                idamCaseWorkerId,
                                JURISDICTION,
                                CASE_TYPE,
                                caseDetails.getId() + ""
                        );

                assertThat(theCase.getId())
                        .isEqualTo(caseDetails.getId());
            }

            @Test
            @DisplayName("Should be able to search to retrieve a case")
            void search() {
                createCaseForCaseworker();

                List<CaseDetails> caseDetails1 = coreCaseDataApi.searchForCaseworker(
                        idamCaseWorkerToken,
                        authTokenGenerator.generate(),
                        idamCaseWorkerId,
                        JURISDICTION,
                        CASE_TYPE,
                        SEARCH_CRITERIA
                );

                assertThat(caseDetails1.size()).isGreaterThan(0);
                assertThat(caseDetails1.get(0).getData().get("TextField")).isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to update a case")
            void update() {
                CaseDetails caseDetails = createCaseForCaseworker();

                StartEventResponse startEventResponse = coreCaseDataApi.startEventForCaseWorker(
                        idamCaseWorkerToken,
                        authTokenGenerator.generate(),
                        idamCaseWorkerId,
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId() + "",
                        UPDATE_CASE_EVENT
                );

                CaseDataContent caseDataContent = CaseDataContent.builder()
                        .eventToken(startEventResponse.getToken())
                        .event(Event.builder().id(startEventResponse.getEventId()).build())
                        .data(UPDATE_CASE_DATA)
                        .build();

                CaseDetails updatedCase = coreCaseDataApi.submitEventForCaseWorker(
                        idamCaseWorkerToken,
                        authTokenGenerator.generate(),
                        idamCaseWorkerId,
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId() + "",
                        true,
                        caseDataContent
                );

                assertThat(updatedCase.getData().get("TextField")).isEqualTo("text updated");
            }
        }

        @Nested
        @DisplayName("Citizen")
        @Disabled("Auto test jurisdiction doesn't have citizen events currently, it's on the CCD backlog to do soon")
        class Citizen {

            @BeforeEach
            void init() {
                createCitizen();
            }

            @Test
            @DisplayName("Should be able to create a case")
            void createCase() {
                CaseDetails caseDetails = createCaseForCitizen();

                assertThat(caseDetails.getData().get("TextField"))
                        .isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to retrieve a case")
            void getCase() {
                CaseDetails caseDetails = createCaseForCitizen();

                CaseDetails theCase = coreCaseDataApi
                        .readForCitizen(
                                idamCitizenToken,
                                authTokenGenerator.generate(),
                                idamCitizenId,
                                JURISDICTION,
                                CASE_TYPE,
                                caseDetails.getId() + ""
                        );

                assertThat(theCase.getId())
                        .isEqualTo(caseDetails.getId());
            }

            @Test
            @DisplayName("Should be able to search to retrieve a case")
            void search() {
                createCaseForCitizen();

                List<CaseDetails> caseDetails1 = coreCaseDataApi.searchForCitizen(
                        idamCitizenToken,
                        authTokenGenerator.generate(),
                        idamCitizenId,
                        JURISDICTION,
                        CASE_TYPE,
                        SEARCH_CRITERIA
                );

                assertThat(caseDetails1.size()).isGreaterThan(0);
                assertThat(caseDetails1.get(0).getData().get("TextField")).isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to update a case")
            void update() {
                CaseDetails caseDetails = createCaseForCitizen();

                StartEventResponse startEventResponse = coreCaseDataApi.startEventForCitizen(
                        idamCitizenToken,
                        authTokenGenerator.generate(),
                        idamCitizenId,
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId() + "",
                        UPDATE_CASE_EVENT
                );

                CaseDataContent caseDataContent = CaseDataContent.builder()
                        .eventToken(startEventResponse.getToken())
                        .event(Event.builder().id(startEventResponse.getEventId()).build())
                        .data(UPDATE_CASE_DATA)
                        .build();

                CaseDetails updatedCase = coreCaseDataApi.submitEventForCitizen(
                        idamCaseWorkerToken,
                        authTokenGenerator.generate(),
                        idamCaseWorkerId,
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId() + "",
                        true,
                        caseDataContent
                );

                assertThat(updatedCase.getData().get("TextField")).isEqualTo("text updated");
            }
        }

    }

    private CaseDetails createCaseForCitizen() {
        StartEventResponse startEventResponse = coreCaseDataApi.startForCitizen(
                idamCitizenToken,
                authTokenGenerator.generate(),
                idamCitizenId,
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
                idamCaseWorkerToken,
                authTokenGenerator.generate(),
                idamCaseWorkerId,
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );
    }


    private CaseDetails createCaseForCaseworker() {
        StartEventResponse startEventResponse = coreCaseDataApi.startForCaseworker(
                idamCaseWorkerToken,
                authTokenGenerator.generate(),
                idamCaseWorkerId,
                JURISDICTION,
                CASE_TYPE,
                CREATE_CASE_EVENT
        );

        CaseDataContent caseDataContent = CaseDataContent.builder()
                .eventToken(startEventResponse.getToken())
                .event(Event.builder().id(startEventResponse.getEventId()).build())
                .data(CREATE_CASE_DATA)
                .build();

        return coreCaseDataApi.submitForCaseworker(
                idamCaseWorkerToken,
                authTokenGenerator.generate(),
                idamCaseWorkerId,
                JURISDICTION,
                CASE_TYPE,
                true,
                caseDataContent
        );
    }

    private void createCaseworker() {
        String caseworkerEmail = "autocaseworker-ccdclient@hmcts.net";
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

    private void createCitizen() {
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

        idamCitizenToken = idamClient.authenticateUser(citizenEmail, CreateUserRequest.DEFAULT_PASSWORD);
        idamCitizenId = idamClient.getUserDetails(idamCitizenToken).getId();
    }
}
