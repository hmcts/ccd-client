package uk.gov.hmcts.reform.ccd.client.model;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The client's models must decode and encode identically whether the consumer's HTTP mapper is
 * Jackson 2 or Jackson 3. Both mappers here fail on unknown properties, so any response model that
 * relies on a mapper default rather than its own annotations fails.
 */
class JacksonCompatibilityTest {

    interface Mapper {
        <T> T read(String json, Class<T> type) throws Exception;

        String write(Object value) throws Exception;
    }

    static Stream<Arguments> mappers() {
        var jackson2 = new com.fasterxml.jackson.databind.ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        var jackson3 = JsonMapper.builder()
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
        return Stream.of(
            Arguments.of("Jackson 2", new Mapper() {
                @Override
                public <T> T read(String json, Class<T> type) throws Exception {
                    return jackson2.readValue(json, type);
                }

                @Override
                public String write(Object value) throws Exception {
                    return jackson2.writeValueAsString(value);
                }
            }),
            Arguments.of("Jackson 3", new Mapper() {
                @Override
                public <T> T read(String json, Class<T> type) {
                    return jackson3.readValue(json, type);
                }

                @Override
                public String write(Object value) {
                    return jackson3.writeValueAsString(value);
                }
            })
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    @SuppressWarnings("unchecked")
    void decodesCreateEventResponse(String name, Mapper mapper) throws Exception {
        CaseResource resource = mapper.read(fixture("createEvent.v2.json"), CaseResource.class);

        assertThat(resource.getReference()).isEqualTo("1790598114368751");
        assertThat(resource.getState()).isEqualTo("Submitted");
        assertThat(resource.getLastModifiedOn()).isEqualTo(LocalDateTime.of(2026, 9, 28, 13, 21, 55, 456_000_000));
        assertThat(resource.getData()).containsEntry("note", "probe note");
        assertThat((Map<String, Object>) resource.getData().get("applicant1"))
            .containsEntry("firstName", "app1_first_name");
        assertThat((List<Object>) resource.getData().get("notes")).hasSize(1);
        assertThat(resource.getDataClassification()).containsEntry("note", "PUBLIC");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void decodesCategoriesAndDocuments(String name, Mapper mapper) throws Exception {
        CategoriesAndDocuments result = mapper.read(fixture("categoriesAndDocuments.json"),
            CategoriesAndDocuments.class);

        assertThat(result.getCaseVersion()).isEqualTo(1);

        Document uncategorised = result.getUncategorisedDocuments().getFirst();
        assertThat(uncategorised.getDocumentURL())
            .isEqualTo("http://localhost/documents/12345678-1234-1234-1234-123456789012");
        assertThat(uncategorised.getDocumentBinaryURL()).endsWith("/binary");
        assertThat(uncategorised.getDocumentFilename()).isEqualTo("test.pdf");
        assertThat(uncategorised.getAttributePath()).isEqualTo("testDocument");

        Category category = result.getCategories().getFirst();
        assertThat(category.getCategoryId()).isEqualTo("evidence");
        assertThat(category.getCategoryName()).isEqualTo("Evidence");
        assertThat(category.getCategoryOrder()).isEqualTo(1);
        assertThat(category.getDocuments().getFirst().getUploadTimestamp())
            .isEqualTo(LocalDateTime.of(2026, 9, 28, 13, 21, 54, 123_000_000));
        assertThat(category.getSubCategories().getFirst().getCategoryId()).isEqualTo("photos");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void decodesCaseAssignmentResponses(String name, Mapper mapper) throws Exception {
        CaseAssignmentUserRolesResource resource = mapper.read(fixture("caseAssignmentUserRoles.json"),
            CaseAssignmentUserRolesResource.class);
        CaseAssignmentUserRole role = resource.getCaseAssignmentUserRoles().getFirst();
        assertThat(role.getCaseDataId()).isEqualTo("1790598114368751");
        assertThat(role.getUserId()).isEqualTo("6e508b49-1fa8-3d3c-8b53-ec466637315b");
        assertThat(role.getCaseRole()).isEqualTo("[APPONESOLICITOR]");

        CaseAssignmentUserRolesResponse response = mapper.read(fixture("caseAssignmentUserRolesResponse.json"),
            CaseAssignmentUserRolesResponse.class);
        assertThat(response.getStatusMessage()).isEqualTo("Case-User-Role assignments returned successfully");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void decodesCaseRetrievalResponses(String name, Mapper mapper) throws Exception {
        CaseDetails caseDetails = mapper.read(fixture("case.v2.json"), CaseDetails.class);
        assertThat(caseDetails.getId()).isEqualTo(1234L);
        assertThat(caseDetails.getCaseTypeId()).isEqualTo("someCaseType");
        assertThat(caseDetails.getVersion()).isEqualTo(99);
        assertThat(caseDetails.getData()).containsKey("someProp");

        StartEventResponse startEvent = mapper.read(fixture("startCase.v2.json"), StartEventResponse.class);
        assertThat(startEvent.getToken()).isNotBlank();

        SearchResult searchResult = mapper.read(fixture("searchResult.json"), SearchResult.class);
        assertThat(searchResult.getTotal()).isEqualTo(10);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void decodesCallbackRequestAndEncodesResponse(String name, Mapper mapper) throws Exception {
        CallbackRequest request = mapper.read("""
            {"event_id":"caseworker-add-note","ignore_warning":false,
             "case_details":{"id":1234,"jurisdiction":"DIVORCE","case_type_id":"E2E","state":"Submitted",
               "created_date":"2026-09-28T13:21:54.123","case_data":{"note":"x"},"security_classification":"PUBLIC",
               "version":3,"last_state_modified_date":"2026-09-28T13:21:54.123"},
             "case_details_before":{"id":1234,"case_data":{}}}
            """, CallbackRequest.class);
        assertThat(request.getEventId()).isEqualTo("caseworker-add-note");
        assertThat(request.getCaseDetails().getData()).containsEntry("note", "x");
        assertThat(request.getCaseDetails().getVersion()).isEqualTo(3);
        assertThat(request.getCaseDetailsBefore().getId()).isEqualTo(1234L);

        var reference = JsonMapper.builder().build();
        var response = AboutToStartOrSubmitCallbackResponse.builder()
            .data(Map.of("note", "y"))
            .errors(List.of("an error"))
            .state("Submitted")
            .build();
        assertThat(reference.readTree(mapper.write(response)).get("data").get("note").asString()).isEqualTo("y");
        assertThat(reference.readTree(mapper.write(response)).get("errors").get(0).asString()).isEqualTo("an error");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void decodesCaseUser(String name, Mapper mapper) throws Exception {
        CaseUser user = mapper.read("{\"user_id\":\"u1\",\"case_roles\":[\"[CREATOR]\"]}", CaseUser.class);
        assertThat(user.getUserId()).isEqualTo("u1");
        assertThat(user.getCaseRoles()).containsExactly("[CREATOR]");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("mappers")
    void encodesRequestsIdentically(String name, Mapper mapper) throws Exception {
        var reference = JsonMapper.builder().build();
        var content = CaseDataContent.builder()
            .eventToken("token")
            .event(Event.builder().id("caseworker-add-note").summary("summary").description("description").build())
            .data(Map.of("note", "a note"))
            .caseReference("1234")
            .build();
        var assignment = CaseAssignmentUserRolesRequest.builder()
            .caseAssignmentUserRolesWithOrganisation(List.of(CaseAssignmentUserRoleWithOrganisation.builder()
                .caseDataId("1234").userId("u1").caseRole("[CREATOR]").organisationId("ORG1").build()))
            .build();

        assertThat(reference.readTree(mapper.write(content)))
            .isEqualTo(reference.readTree("""
                {"event":{"id":"caseworker-add-note","summary":"summary","description":"description"},
                 "data":{"note":"a note"},"supplementary_data_request":null,"security_classification":null,
                 "event_token":"token","ignore_warning":false,"case_reference":"1234"}
                """));
        assertThat(reference.readTree(mapper.write(assignment)))
            .isEqualTo(reference.readTree("""
                {"case_users":[{"case_id":"1234","user_id":"u1","case_role":"[CREATOR]",
                  "organisation_id":"ORG1"}]}
                """));
    }

    private static String fixture(String name) throws IOException {
        try (InputStream in = JacksonCompatibilityTest.class.getResourceAsStream("/" + name)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
