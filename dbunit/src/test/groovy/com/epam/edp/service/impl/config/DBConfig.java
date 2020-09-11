package com.epam.edp.service.impl.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import javax.sql.DataSource;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DBConfig {

  @Bean
  public DatabaseConfigBean configBean() {
    final DatabaseConfigBean configBean = new DatabaseConfigBean();
    configBean.setDatatypeFactory(new DefaultDataTypeFactory());
    return configBean;
  }

  @Bean(name = "employeeDbConnection")
  public DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean(DatabaseConfigBean configBean,
      @Qualifier("employeeDataSource") DataSource dataSource) {
    DatabaseDataSourceConnectionFactoryBean factoryBean = new DatabaseDataSourceConnectionFactoryBean();
    factoryBean.setDatabaseConfig(configBean);
    factoryBean.setDataSource(dataSource);
    return factoryBean;
  }

}
