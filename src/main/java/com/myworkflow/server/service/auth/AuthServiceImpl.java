package com.myworkflow.server.service.auth;

import com.myworkflow.server.entity.auth.TempTokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/10/2017
 * email: code.crosser@gmail.com
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final String SECRET_KEY = "mySecretKey";
    private static final int MINUTES = 20;

    public TempTokenEntity createTempToken() {

        TempTokenEntity tempToken = new TempTokenEntity();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key signingKey = new SecretKeySpec(apiSecretKeyBytes, signatureAlgorithm.getJcaName());

        Calendar calendar = Calendar.getInstance();
        tempToken.setLocalServerTime(calendar.getTimeInMillis());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(calendar.getTime())
                .signWith(signatureAlgorithm, signingKey);

        calendar.add(Calendar.MINUTE, MINUTES);
        tempToken.setExpirationTime(calendar.getTimeInMillis());

        jwtBuilder.setExpiration(calendar.getTime());
        tempToken.setTokenBody(jwtBuilder.compact());

        return tempToken;
    }

    public boolean verifyTempToken(TempTokenEntity tempTokenEntity) {

        if (tempTokenEntity != null) {

            String tokenString = tempTokenEntity.getTokenBody();

            if(tokenString != null) {

                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                        .parseClaimsJws(tokenString).getBody();

                long tokenExpiration = claims.getExpiration().getTime();
                if (tokenExpiration > System.currentTimeMillis()) {
                    return true;
                }
            }
        }
        return false;
    }

}