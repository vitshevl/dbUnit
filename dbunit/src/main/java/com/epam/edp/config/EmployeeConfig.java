package com.epam.edp.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.epam.edp.repository"},
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "employeeTransactionManager"
)
@Profile({"prod"})
public class EmployeeConfig extends AbstractPersistenceContextConfig {


  private final PropertyResolver env;
  private static final String NAME = "employee";


  @Primary
  @Bean(name = "employeeDataSource")
  @ConfigurationProperties(prefix = "employee.datasource")
  public DataSource dataSource() {
    return getDataSource(env, NAME);
  }

  @Primary
  @Bean(name = "entityManagerFactory")
  @DependsOn("employeeLiquibase")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("employeeDataSource") DataSource dataSource
  ) {
    return getLocalContainerEntityManagerFactoryBean(env,
        builder,
        dataSource,
        NAME,
        "com.epam.edp.domain.employee");
  }

  @Primary
  @Bean(name = "employeeTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
  ) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
