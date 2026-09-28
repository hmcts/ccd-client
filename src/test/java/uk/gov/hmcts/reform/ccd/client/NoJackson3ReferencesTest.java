package uk.gov.hmcts.reform.ccd.client;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The client must not reference Jackson 3 types so it can be used by consumers whose Jackson 2
 * classpath guard forbids them. Only the annotations shared by Jackson 2 and Jackson 3
 * ({@code com.fasterxml.jackson.annotation}) are allowed.
 */
class NoJackson3ReferencesTest {

    private static final byte[] JACKSON3_PACKAGE = "tools/jackson/".getBytes(StandardCharsets.UTF_8);
    private static final byte[] JACKSON2_DATABIND = "com/fasterxml/jackson/databind/".getBytes(StandardCharsets.UTF_8);

    @Test
    void compiledClassesUseOnlySharedJacksonAnnotations() throws IOException, URISyntaxException {
        Path classes = Path.of(CoreCaseDataApi.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        List<String> offenders;
        try (Stream<Path> files = Files.walk(classes)) {
            offenders = files
                .filter(file -> file.toString().endsWith(".class"))
                .filter(file -> {
                    byte[] bytes = read(file);
                    return contains(bytes, JACKSON3_PACKAGE) || contains(bytes, JACKSON2_DATABIND);
                })
                .map(file -> classes.relativize(file).toString())
                .toList();
        }

        assertThat(offenders).isEmpty();
    }

    private static byte[] read(Path file) {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static boolean contains(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }
}
