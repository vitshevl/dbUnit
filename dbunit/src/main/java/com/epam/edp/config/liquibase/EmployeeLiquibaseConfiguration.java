package com.epam.edp.config.liquibase;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class EmployeeLiquibaseConfiguration extends LiquibaseConfig{

  @Bean
  @ConfigurationProperties(prefix = "employee.liquibase")
  public LiquibaseProperties liquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean(name = "employeeLiquibase")
  public SpringLiquibase liquibase(@Qualifier("employeeDataSource") DataSource dataSource) {
    return springLiquibase(dataSource, liquibaseProperties());
  }

}
