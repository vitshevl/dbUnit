package com.epam.edp.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.core.env.PropertyResolver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public abstract class AbstractPersistenceContextConfig {

   protected DataSource getDataSource(PropertyResolver env, String name) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(env.getProperty(name + ".datasource.url"));
    String driver = env.getProperty(name + ".datasource.driver-class-name");
    if (driver != null && !driver.isEmpty()) {
      dataSource.setDriverClassName(driver);
    }
    dataSource.setUsername(env.getProperty(name + ".datasource.username"));
    dataSource.setPassword(env.getProperty(name + ".datasource.password"));
    return dataSource;
  }

  protected LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(
      PropertyResolver env,
      EntityManagerFactoryBuilder builder,
      DataSource dataSource,
      String name,
      String packageName) {
    Map<String, Object> properties = new ConcurrentHashMap<>();
    properties.put("hibernate.temp.use_jdbc_metadata_defaults", Boolean.FALSE);

    String dialect = env.getProperty(name + ".datasource.dialect");
    if (!StringUtils.isEmpty(dialect)) {
      properties.put("hibernate.dialect", dialect);
    }

    String ddlSetting = env.getProperty(name + ".datasource.ddl-auto");
    if (!StringUtils.isEmpty(ddlSetting)) {
      properties.put("hibernate.hbm2ddl.auto", ddlSetting);
    }

    String physicalStrategy = env.getProperty(name + ".datasource.physical-naming-strategy");
    if (!StringUtils.isEmpty(physicalStrategy)) {
      properties.put("hibernate.physical_naming_strategy", physicalStrategy);
    }

    return builder
        .dataSource(dataSource)
        .packages(packageName)
        .persistenceUnit(name)
        .properties(properties)
        .build();
  }

}
