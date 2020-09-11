package com.epam.edp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBUnitRequestBody {

  String employeeFirstName;
  String employeeMiddleName;
  String employeeLastName;
  String employeeEmail;

}
