package com.myworkflow.server.controller.auth;

import com.myworkflow.server.entity.UserEntity;
import com.myworkflow.server.entity.auth.RealTokenEntity;
import com.myworkflow.server.entity.auth.TempTokenEntity;
import com.myworkflow.server.entity.requsets.auth.SignInRequest;
import com.myworkflow.server.entity.requsets.auth.SignUpRequest;
import com.myworkflow.server.entity.responses.auth.MessageResponse;
import com.myworkflow.server.entity.responses.auth.SignInResponse;
import com.myworkflow.server.entity.responses.auth.SignUpResponse;
import com.myworkflow.server.service.UserService;
import com.myworkflow.server.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    private static final String sessionTokenName = "sessionToken";

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @RequestMapping(value = "/auth", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> getToken(HttpServletRequest request) {

        HttpSession httpSession = request.getSession();
        TempTokenEntity tempTokenEntity = authService.createTempToken();
        //createAndSave temp token in session
        httpSession.setAttribute(sessionTokenName, tempTokenEntity);

        return new ResponseEntity<>(tempTokenEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(HttpServletRequest request, @RequestBody final SignUpRequest signUpRequest) {

        String requestToken = signUpRequest.getAuthToken();

        HttpSession httpSession = request.getSession();
        TempTokenEntity sessionToken = (TempTokenEntity) httpSession.getAttribute(sessionTokenName);

        if (sessionToken != null && sessionToken.getTokenBody() != null) {

            //verify request token with token from session
            if (sessionToken.getTokenBody().equals(requestToken) && authService.isTokenValidByTime(sessionToken)) {

                //check user name existence
                if(userService.findByLogin(signUpRequest.getUserName()) != null){
                    return new ResponseEntity<>(new MessageResponse(HttpStatus.CONFLICT.toString(), "user name exist"), HttpStatus.CONFLICT);
                }

                //create and store user
                UserEntity userEntity = userService.createAndSave(signUpRequest.getUserName(), signUpRequest.getPassword());

                //create and store real token
                RealTokenEntity realTokenEntity = authService.createAndSaveRealToken(userEntity.getUserId());

                //return real token and 200 code
                SignUpResponse signUpResponse = new SignUpResponse(HttpStatus.OK.toString(), realTokenEntity.getRealTokenBody());
                return new ResponseEntity<>(signUpResponse, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.UNAUTHORIZED.toString(), "bad auth token"), HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.toString(), "bad request"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/signin", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(HttpServletRequest request, @RequestBody final SignInRequest signUpRequest) {

        String requestToken = signUpRequest.getAuthToken();

        HttpSession httpSession = request.getSession();
        TempTokenEntity sessionToken = (TempTokenEntity) httpSession.getAttribute(sessionTokenName);

        if (sessionToken != null && sessionToken.getTokenBody() != null) {

            //verify request token with token from session
            if (sessionToken.getTokenBody().equals(requestToken) && authService.isTokenValidByTime(sessionToken)) {

                //check user existence
                UserEntity userEntity = userService.findByLoginAndPassword(signUpRequest.getUserName(), signUpRequest.getPassword());
                if(userEntity != null){

                    //create and store real token
                    RealTokenEntity realTokenEntity = authService.createAndSaveRealToken(userEntity.getUserId());

                    //return real token and 200 code
                    SignInResponse signIpResponse = new SignInResponse(HttpStatus.OK.toString(), realTokenEntity.getRealTokenBody());
                    return new ResponseEntity<>(signIpResponse, HttpStatus.OK);                }

            } else {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.UNAUTHORIZED.toString(), "bad auth token"), HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.toString(), "bad request"), HttpStatus.BAD_REQUEST);
    }

}
