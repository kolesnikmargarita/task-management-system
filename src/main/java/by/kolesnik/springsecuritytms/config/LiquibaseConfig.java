package by.kolesnik.springsecuritytms.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase springLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("liquibase/changelog-master.xml");
        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + System.getenv("DB_HOST") + "/" + System.getenv("DB_NAME"));
        dataSource.setUsername(System.getenv("POSTGRES_USERNAME"));
        dataSource.setPassword(System.getenv("POSTGRES_PASSWORD"));
        return dataSource;
    }
}
