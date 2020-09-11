package com.epam.edp.service;

import com.epam.edp.model.DBUnitRequestBody;

public interface UseCase {

  Long saveRequest(DBUnitRequestBody requestBody);

}
