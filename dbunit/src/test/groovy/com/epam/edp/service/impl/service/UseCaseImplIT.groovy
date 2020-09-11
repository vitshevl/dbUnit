package com.epam.edp.service.impl.service

import com.epam.edp.Application
import com.epam.edp.model.DBUnitRequestBody
import com.epam.edp.service.UseCase
import com.epam.edp.service.impl.config.DBConfig
import com.epam.edp.service.impl.config.EmployeeTestContainerConfig
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DbUnitConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [Application.class, EmployeeTestContainerConfig.class, DBConfig.class])
@DbUnitConfiguration(databaseConnection = "employeeDbConnection")
@TestExecutionListeners([TransactionDbUnitTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    MockitoTestExecutionListener.class])
class UseCaseImplIT {

  @Autowired
  private UseCase useCase

  @Test
  void test() {
    given:
    DBUnitRequestBody request = new DBUnitRequestBody()
    request.setEmployeeFirstName("test")
    request.setEmployeeMiddleName("test")
    request.setEmployeeLastName("test")
    request.setEmployeeEmail("test")

    when:
    Long id = useCase.saveRequest(request)

    then:
    assert id == 2

  }

}
