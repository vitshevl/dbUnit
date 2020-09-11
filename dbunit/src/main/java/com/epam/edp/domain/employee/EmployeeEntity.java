package com.epam.edp.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "employee_first_name")
  private String employeeFirstName;

  @Column(name = "employee_middle_name")
  private String employeeMiddleName;

  @Column(name = "employee_last_name")
  private String employeeLastName;

  @Column(name = "employee_email")
  private String employeeEmail;

}
