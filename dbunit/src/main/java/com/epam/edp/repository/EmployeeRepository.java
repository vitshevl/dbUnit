package com.epam.edp.repository;

import com.epam.edp.domain.employee.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {

}


