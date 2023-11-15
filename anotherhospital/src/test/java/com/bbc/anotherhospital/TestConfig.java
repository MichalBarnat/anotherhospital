package com.bbc.anotherhospital;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.h2.jdbcx.JdbcDataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb");
        dataSource.setUser("sa");
        dataSource.setPassword("password");

        return new NamedParameterJdbcTemplate(dataSource);
    }
}