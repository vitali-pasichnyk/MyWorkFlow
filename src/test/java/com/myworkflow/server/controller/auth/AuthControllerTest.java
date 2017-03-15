package com.myworkflow.server.controller.auth;

import com.myworkflow.server.config.WebConfig;
import com.myworkflow.server.entity.UserEntity;
import com.myworkflow.server.entity.auth.RealTokenEntity;
import com.myworkflow.server.entity.auth.TempTokenEntity;
import com.myworkflow.server.entity.requsets.auth.SignUpRequest;
import com.myworkflow.server.service.UserService;
import com.myworkflow.server.service.auth.AuthService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by User
 * creation date: 14-Mar-17
 * email: code.crosser@gmail.com
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getToken_success() throws Exception {

        String tokenBody = "gajdgjasgdh3ad7.asjkdghajdsgjh3agsdj.kjzaygwudbgad67awdugye3d";
        long localServerTime = 1489528305956L;
        long expirationTime = localServerTime + 1200000L;
        TempTokenEntity tokenEntity = new TempTokenEntity(tokenBody, localServerTime, expirationTime);

        when(authService.createTempToken()).thenReturn(tokenEntity);

        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.authtoken", is(tokenBody)))
                .andExpect(jsonPath("$.localServerTime", is(localServerTime)))
                .andExpect(jsonPath("$.expirationTime", is(expirationTime)));

        verify(authService, times(1)).createTempToken();
        verifyNoMoreInteractions(authService);
    }

    @Test
    public void signUp_success() throws Exception {

        String newUser = "newUser";
        String newPass = "newPass";
        String realTokenBody = "realToken";
        String sessionToken = "sessionToken";

        TempTokenEntity tempTokenEntity = new TempTokenEntity(sessionToken, 10L, 15L);
        RealTokenEntity realTokenEntity = new RealTokenEntity(realTokenBody);
        SignUpRequest signUpRequest = new SignUpRequest(sessionToken, newUser, newPass);

        when(authService.isTokenValidByTime(tempTokenEntity)).thenReturn(true);
        when(authService.createAndSaveRealToken(any(Long.class))).thenReturn(realTokenEntity);
        when(userService.findByLogin(newUser)).thenReturn(null);
        when(userService.createAndSave(newUser, newPass)).thenReturn(new UserEntity(newUser, newPass));

        mockMvc.perform(post("/signup")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signUpRequest))
                .sessionAttr("sessionToken", tempTokenEntity)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("200")))
                .andExpect(jsonPath("$.token", is(realTokenBody)));

        verify(authService, times(1)).isTokenValidByTime(tempTokenEntity);
        verify(userService, times(1)).findByLogin(newUser);
        verify(userService, times(1)).createAndSave(newUser, newPass);
    }

    @Test
    public void signUp_conflict_user_name_exist() throws Exception {

        String newUser = "newUser";
        String newPass = "newPass";
        String sessionToken = "sessionToken";
        String errorMessage = "user name exist";

        TempTokenEntity tempTokenEntity = new TempTokenEntity(sessionToken, 10L, 15L);
        SignUpRequest signUpRequest = new SignUpRequest(sessionToken, newUser, newPass);
        UserEntity userEntity = new UserEntity(newUser, newPass);

        when(authService.isTokenValidByTime(tempTokenEntity)).thenReturn(true);
        when(userService.findByLogin(newUser)).thenReturn(userEntity);

        mockMvc.perform(post("/signup")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signUpRequest))
                .sessionAttr("sessionToken", tempTokenEntity)
        )
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("409")))
                .andExpect(jsonPath("$.message", is(errorMessage)));

        verify(authService, times(1)).isTokenValidByTime(tempTokenEntity);
        verify(userService, times(1)).findByLogin(newUser);

    }

    @Test
    public void signUp_unauthorized_bad_auth_token() throws Exception {

        String newUser = "newUser";
        String newPass = "newPass";
        String sessionToken = "sessionToken";
        String errorMessage = "bad auth token";

        TempTokenEntity tempTokenEntity = new TempTokenEntity(sessionToken, 10L, 15L);
        SignUpRequest signUpRequest = new SignUpRequest(sessionToken, newUser, newPass);

        when(authService.isTokenValidByTime(tempTokenEntity)).thenReturn(false);

        mockMvc.perform(post("/signup")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signUpRequest))
                .sessionAttr("sessionToken", tempTokenEntity)
        )
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("401")))
                .andExpect(jsonPath("$.message", is(errorMessage)));

        verify(authService, times(1)).isTokenValidByTime(tempTokenEntity);

    }

    @Test
    public void signUp_bad_request_bad_request() throws Exception {

        String newUser = "";
        String newPass = "";
        String sessionToken = "";
        String errorMessage = "bad request";

        TempTokenEntity tempTokenEntity = new TempTokenEntity(sessionToken, 10L, 15L);
        SignUpRequest signUpRequest = new SignUpRequest(sessionToken, newUser, newPass);

        //because of fake token
        when(authService.isTokenValidByTime(tempTokenEntity)).thenReturn(true);

        mockMvc.perform(post("/signup")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(signUpRequest))
                .sessionAttr("sessionToken", tempTokenEntity)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("400")))
                .andExpect(jsonPath("$.message", is(errorMessage)));

    }
}