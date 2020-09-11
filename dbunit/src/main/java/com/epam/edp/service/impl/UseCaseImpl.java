package com.epam.edp.service.impl;

import com.epam.edp.domain.employee.EmployeeEntity;
import com.epam.edp.model.DBUnitRequestBody;
import com.epam.edp.repository.EmployeeRepository;
import com.epam.edp.service.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UseCaseImpl implements UseCase {

  private final EmployeeRepository employeeRepository;


  @Override
  public Long saveRequest(DBUnitRequestBody requestBody) {

    EmployeeEntity employeeEntity = new EmployeeEntity();

    employeeEntity.setEmployeeFirstName(requestBody.getEmployeeFirstName());
    employeeEntity.setEmployeeMiddleName(requestBody.getEmployeeMiddleName());
    employeeEntity.setEmployeeLastName(requestBody.getEmployeeLastName());
    employeeEntity.setEmployeeEmail(requestBody.getEmployeeEmail());

   employeeRepository.save(employeeEntity);

    return employeeEntity.getId();
  }
}
