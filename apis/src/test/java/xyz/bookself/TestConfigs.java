package xyz.bookself;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfigs {
    @Bean
    public DataSource dataSource() {
        return Mockito.mock(DataSource.class);
    }
}
