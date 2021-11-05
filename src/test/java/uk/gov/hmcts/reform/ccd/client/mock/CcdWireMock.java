package uk.gov.hmcts.reform.ccd.client.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.google.common.io.Resources;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CcdWireMock {
    private static final WireMockServer SERVER = new WireMockServer(8081);

    private CcdWireMock() {
    }

    public static void start() {
        if (!SERVER.isRunning()) {
            SERVER.start();
            System.setProperty("core_case_data.api.url", "http://localhost:" + SERVER.port() + "/");
        }
    }

    public static void stopAndReset() {
        if (SERVER.isRunning()) {
            SERVER.stop();
            SERVER.resetAll();
        }
    }

    public static void stub(MappingBuilder url, String filename) throws IOException {
        SERVER.stubFor(
            url
                .withHeader(AUTHORIZATION, equalTo("UserToken"))
                .withHeader("ServiceAuthorization", equalTo("s2sAuth"))
                .willReturn(okJson(loadFile(filename)))
        );
    }

    private static String loadFile(String filename) throws IOException {
        return Resources.toString(ClassLoader.getSystemClassLoader().getResource(filename), UTF_8);
    }

}
