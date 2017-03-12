package com.myworkflow.server.controller.auth;

import com.myworkflow.server.entity.auth.TempTokenEntity;
import com.myworkflow.server.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/10/2017
 * email: code.crosser@gmail.com
 */
@RestController
public class AuthController {

    private final AuthService authService;
    private static final String sessionTokenName = "sessionToken";

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public TempTokenEntity getToken(HttpServletRequest request) {

        HttpSession httpSession = request.getSession();
        TempTokenEntity tempTokenEntity = authService.createTempToken();
        httpSession.setAttribute(sessionTokenName, tempTokenEntity);

        return tempTokenEntity;
    }

    @RequestMapping(value = "/canSignUp", method = RequestMethod.POST)
    public boolean signUp(HttpServletRequest request, @RequestBody final TempTokenEntity requestToken) {

        HttpSession httpSession = request.getSession();
        TempTokenEntity sessionToken = (TempTokenEntity) httpSession.getAttribute(sessionTokenName);

        //noinspection SimplifiableIfStatement
        if (sessionToken.equals(requestToken)) {
            return authService.verifyTempToken(requestToken);
        }
        return false;
    }

}
