package xyz.bookself.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegrationTests extends AbstractIT {

    @Test
    void test() {
        Assertions.assertNotNull(postgreDBContainer);
        Assertions.assertDoesNotThrow(() -> authorRepository.findAnyAuthors(1));
    }

}
