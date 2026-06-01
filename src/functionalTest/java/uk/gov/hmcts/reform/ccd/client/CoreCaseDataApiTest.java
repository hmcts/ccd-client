package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import uk.gov.hmcts.reform.ccd.client.model.CaseDataContent;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.CaseResource;
import uk.gov.hmcts.reform.ccd.client.model.Event;
import uk.gov.hmcts.reform.ccd.client.model.StartEventResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@EnableAutoConfiguration
@DisplayName("Core case data api")
class CoreCaseDataApiTest extends BaseTest {

    private static final Map<String, String> UPDATE_CASE_DATA = Map.of("TextField", "text updated");

    private static final Map<String, String> SEARCH_CRITERIA = Map.of("case.TextField", "text...");

    private User caseWorker;
    private User citizen;

    @Nested
    @DisplayName("V1")
    class V1 {

        @Nested
        @DisplayName("Case worker")
        class CaseWorker {

            @BeforeEach
            void init() {
                caseWorker = createCaseworker();
            }

            @Test
            @DisplayName("Should be able to create a case")
            void createCase() {
                CaseDetails caseDetails = createCaseForCaseworker(caseWorker);

                assertThat(caseDetails.getData().get("TextField"))
                        .isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to retrieve a case")
            void getCase() {
                CaseDetails caseDetails = createCaseForCaseworker(caseWorker);

                CaseDetails theCase = coreCaseDataApi.readForCaseWorker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString()
                );

                assertThat(theCase.getId())
                        .isEqualTo(caseDetails.getId());
            }

            @Test
            @DisplayName("Should be able to search to retrieve a case")
            void search() {
                createCaseForCaseworker(caseWorker);

                List<CaseDetails> caseDetails = coreCaseDataApi.searchForCaseworker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        SEARCH_CRITERIA
                );

                assertThat(caseDetails).isNotEmpty();
                assertThat(caseDetails.get(0).getData().get("TextField")).isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to update a case")
            void update() {
                CaseDetails caseDetails = createCaseForCaseworker(caseWorker);

                StartEventResponse startEventResponse = coreCaseDataApi.startEventForCaseWorker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
                        UPDATE_CASE_EVENT
                );

                CaseDataContent caseDataContent = CaseDataContent.builder()
                        .eventToken(startEventResponse.getToken())
                        .event(Event.builder().id(startEventResponse.getEventId()).build())
                        .data(UPDATE_CASE_DATA)
                        .build();

                CaseDetails updatedCase = coreCaseDataApi.submitEventForCaseWorker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
                        true,
                        caseDataContent
                );

                assertThat(updatedCase.getData().get("TextField")).isEqualTo("text updated");
            }

            @Test
            @DisplayName("Should be able to update a case and ignore warning")
            void updateWithIgnoreWarning() {
                CaseDetails caseDetails = createCaseForCaseworker(caseWorker);

                StartEventResponse startEventResponse = coreCaseDataApi.startEventForCaseWorker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
                        UPDATE_CASE_EVENT,
                        true
                );

                CaseDataContent caseDataContent = CaseDataContent.builder()
                        .eventToken(startEventResponse.getToken())
                        .event(Event.builder().id(startEventResponse.getEventId()).build())
                        .data(UPDATE_CASE_DATA)
                        .build();

                CaseDetails updatedCase = coreCaseDataApi.submitEventForCaseWorker(
                        caseWorker.getAuthToken(),
                        authTokenGenerator.generate(),
                        caseWorker.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
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
                citizen = createCitizen();
            }

            @Test
            @DisplayName("Should be able to create a case")
            void createCase() {
                CaseDetails caseDetails = createCaseForCitizen(citizen);

                assertThat(caseDetails.getData().get("TextField"))
                        .isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to retrieve a case")
            void getCase() {
                CaseDetails caseDetails = createCaseForCitizen(citizen);

                CaseDetails theCase = coreCaseDataApi.readForCitizen(
                        citizen.getAuthToken(),
                        authTokenGenerator.generate(),
                        citizen.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString()
                );

                assertThat(theCase.getId())
                        .isEqualTo(caseDetails.getId());
            }

            @Test
            @DisplayName("Should be able to search to retrieve a case")
            void search() {
                createCaseForCitizen(citizen);

                List<CaseDetails> caseDetails = coreCaseDataApi.searchForCitizen(
                        citizen.getAuthToken(),
                        authTokenGenerator.generate(),
                        citizen.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        SEARCH_CRITERIA
                );

                assertThat(caseDetails).isNotEmpty();
                assertThat(caseDetails.get(0).getData().get("TextField")).isEqualTo("text...");
            }

            @Test
            @DisplayName("Should be able to update a case")
            void update() {
                CaseDetails caseDetails = createCaseForCitizen(citizen);

                StartEventResponse startEventResponse = coreCaseDataApi.startEventForCitizen(
                        citizen.getAuthToken(),
                        authTokenGenerator.generate(),
                        citizen.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
                        UPDATE_CASE_EVENT
                );

                CaseDataContent caseDataContent = CaseDataContent.builder()
                        .eventToken(startEventResponse.getToken())
                        .event(Event.builder().id(startEventResponse.getEventId()).build())
                        .data(UPDATE_CASE_DATA)
                        .build();

                CaseDetails updatedCase = coreCaseDataApi.submitEventForCitizen(
                        citizen.getAuthToken(),
                        authTokenGenerator.generate(),
                        citizen.getUserDetails().getUid(),
                        JURISDICTION,
                        CASE_TYPE,
                        caseDetails.getId().toString(),
                        true,
                        caseDataContent
                );

                assertThat(updatedCase.getData().get("TextField")).isEqualTo("text updated");
            }
        }
    }

    @Nested
    @DisplayName("V2")
    class V2 {

        @BeforeEach
        void init() {
            caseWorker = createCaseworker();
        }

        @Test
        @DisplayName("Should be able to create a case")
        void create() {
            CaseDetails caseDetails = createCase(caseWorker);

            assertThat(caseDetails.getData().get("TextField"))
                    .isEqualTo("text...");
        }

        @Test
        @DisplayName("Should be able to retrieve a case")
        void getCase() {
            CaseDetails caseDetails = createCase(caseWorker);

            CaseDetails theCase = coreCaseDataApi.getCase(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseDetails.getId().toString()
            );

            assertThat(theCase.getId())
                    .isEqualTo(caseDetails.getId());
        }

        @Test
        @DisplayName("Should be able to update a case")
        void update() {
            CaseDetails caseDetails = createCaseForCaseworker(caseWorker);

            StartEventResponse startEventResponse = coreCaseDataApi.startEvent(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseDetails.getId().toString(),
                    UPDATE_CASE_EVENT
            );

            CaseDataContent caseDataContent = CaseDataContent.builder()
                    .eventToken(startEventResponse.getToken())
                    .event(Event.builder().id(startEventResponse.getEventId()).build())
                    .data(UPDATE_CASE_DATA)
                    .build();

            CaseDetails updatedCase = coreCaseDataApi.submitEventForCaseWorker(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseWorker.getUserDetails().getUid(),
                    JURISDICTION,
                    CASE_TYPE,
                    caseDetails.getId().toString(),
                    true,
                    caseDataContent
            );

            assertThat(updatedCase.getData().get("TextField")).isEqualTo("text updated");
        }

        @Test
        @DisplayName("Should be able to store supplementary data")
        void saveSupplementaryData() {
            Map<String, Object> hmctsServiceIdMap = new HashMap<>();
            hmctsServiceIdMap.put("HMCTSServiceId", "BBA3");

            Map<String, Map<String, Object>> supplementaryDataRequestMap = new HashMap<>();
            supplementaryDataRequestMap.put("$set", hmctsServiceIdMap);

            Map<String, Map<String, Map<String, Object>>> supplementaryDataUpdates = new HashMap<>();
            supplementaryDataUpdates.put("supplementary_data_updates", supplementaryDataRequestMap);

            CaseDetails caseDetails = createCase(caseWorker);

            assertThatCode(() -> coreCaseDataApi.submitSupplementaryData(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseDetails.getId().toString(),
                    supplementaryDataUpdates
            )).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Should be able to submit an event for a case")
        void createEventForCase() {
            CaseDetails caseDetails = createCase(caseWorker);

            StartEventResponse startEventResponse = coreCaseDataApi.startEvent(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseDetails.getId().toString(),
                    UPDATE_CASE_EVENT
            );

            CaseDataContent caseDataContent = CaseDataContent.builder()
                    .caseReference(startEventResponse.getCaseDetails().getId().toString())
                    .data(startEventResponse.getCaseDetails().getData())
                    .event(Event.builder().id(UPDATE_CASE_EVENT).build())
                    .eventToken(startEventResponse.getToken())
                    .build();

            CaseResource theCase = coreCaseDataApi.createEvent(
                    caseWorker.getAuthToken(),
                    authTokenGenerator.generate(),
                    caseDetails.getId().toString(),
                    caseDataContent
            );

            assertThat(theCase.getReference())
                    .isEqualTo(caseDetails.getId().toString());
            assertThat(theCase.getData().get("TextField").asText())
                    .isEqualTo(caseDetails.getData().get("TextField").toString());
            assertThat(theCase.getState()).isEqualTo("IN_PROGRESS");
        }
    }
}
