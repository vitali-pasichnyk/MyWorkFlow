package com.myworkflow.server.service.auth;

import com.myworkflow.server.entity.auth.TempTokenEntity;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/10/2017
 * email: code.crosser@gmail.com
 */
public interface AuthService {

    TempTokenEntity createTempToken();

    boolean verifyTempToken(TempTokenEntity tempTokenEntity);

}
