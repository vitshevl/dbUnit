package com.epam.edp.controller;


import com.epam.edp.model.DBUnitRequestBody;
import com.epam.edp.model.DBUnitResponseBody;
import com.epam.edp.service.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TestController {

  @Autowired
  private final UseCase useCase;


  @PostMapping("/dbunit")
  public DBUnitResponseBody execute(@RequestBody DBUnitRequestBody request) {

    DBUnitResponseBody dbUnitResponseBody = new DBUnitResponseBody();

    dbUnitResponseBody.setId(useCase.saveRequest(request));
    return dbUnitResponseBody;
  }

}
