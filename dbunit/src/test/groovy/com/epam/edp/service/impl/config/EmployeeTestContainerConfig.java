package com.epam.edp.service.impl.config;


import static java.lang.String.format;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.epam.edp.repository"},
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "employeeTransactionManager"
)
@Profile("test")
public class EmployeeTestContainerConfig {


  @Bean(name = "postgreSQLContainer")
  public PostgreSQLContainer postgreSQLContainer() {
    PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(
        "postgres:11")
        .withDatabaseName("employee")
        .withUsername("postgres")
        .withPassword("postgres")
        .withStartupTimeout(Duration.ofSeconds(600));
    postgreSQLContainer.start();

    return postgreSQLContainer;
  }


  @Bean(name = "employeeDataSource")
  @ConfigurationProperties(prefix = "employee-db.datasource")
  @ConditionalOnBean(name = "postgreSQLContainer")
  public DataSource dataSource(PostgreSQLContainer postgreSQLContainer) {
    return getDataSource(postgreSQLContainer);
  }


  @Bean(name = "entityManagerFactory")
  @DependsOn("employeeLiquibase")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("employeeDataSource") DataSource dataSource
  ) {
    return getLocalContainerEntityManagerFactoryBean(
        builder,
        dataSource,
        "employee",
        "com.epam.edp.domain.employee");
  }

  @Bean(name = "employeeTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
  ) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  @ConfigurationProperties(prefix = "employee-db.liquibase")
  public LiquibaseProperties liquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean(name = "employeeLiquibase")
  public SpringLiquibase employeeLiquibase(@Qualifier("employeeDataSource") DataSource dataSource) {
    return springLiquibase(dataSource, liquibaseProperties());
  }




  private DataSource getDataSource(PostgreSQLContainer postgreSQLContainer) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(format("jdbc:postgresql://%s:%s/%s", postgreSQLContainer.getContainerIpAddress(),
        postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT), postgreSQLContainer.getDatabaseName()));
    dataSource.setDriverClassName(Objects.requireNonNull(postgreSQLContainer.getDriverClassName()));
    dataSource.setUsername(postgreSQLContainer.getUsername());
    dataSource.setPassword(postgreSQLContainer.getPassword());

    return dataSource;
  }



  private LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(
      EntityManagerFactoryBuilder builder,
      DataSource dataSource,
      String name,
      String packageName) {
    Map<String, Object> properties = new ConcurrentHashMap<>();
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.put("hibernate.temp.use_jdbc_metadata_defaults", Boolean.FALSE);
    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.physical_naming_strategy",
        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");

    return builder
        .dataSource(dataSource)
        .packages(packageName)
        .persistenceUnit(name)
        .properties(properties)
        .build();
  }

  private SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(properties.getChangeLog());
    liquibase.setContexts(properties.getContexts());
    liquibase.setDefaultSchema(properties.getDefaultSchema());
    liquibase.setDropFirst(properties.isDropFirst());
    liquibase.setShouldRun(properties.isEnabled());
    liquibase.setLabels(properties.getLabels());
    liquibase.setChangeLogParameters(properties.getParameters());
    liquibase.setRollbackFile(properties.getRollbackFile());
    return liquibase;
  }


}
