package com.zyq.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyq.enums.BlogErrorInfoEnum;
import com.zyq.exception.BlogException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class JWTUtils {
    /**
     * 密钥
     */
    private static final String SECRET = "hikari_secret";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 60 * 60 * 1000L;//单位为毫秒秒

    /**
     * 签发者
     */
    private static final String ISSUER = "hikari";

    private static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    private static JWTCreator.Builder getJWTBuilder(String subject, Long ttlMillis, String uuid) {
        Date expireDate = null;
        //过期时间
        if (Objects.isNull(ttlMillis)) {
            expireDate = new Date(System.currentTimeMillis() + EXPIRATION);
        } else {
            expireDate = new Date(System.currentTimeMillis() + ttlMillis);
        }
        return JWT.create()
                .withSubject(subject)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate);
    }



    public static String createToken(String Subject) {
        return getJWTBuilder(Subject, null, getUUID())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static String createToken(String Subject, Long ttMillis) {
        return getJWTBuilder(Subject, ttMillis, getUUID())
                .sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * @author hikari
     * @param Subject   json数据
     * @param ttMillis  过期时长
     * @param id        id
     * @return
     **/
    public static String createToken(String Subject, Long ttMillis, String id) {
        return getJWTBuilder(Subject, ttMillis, id)
                .sign(Algorithm.HMAC256(SECRET));
    }
    /**
     * @author hikari
     * @param token
     * @return json
     **/
    public static String parseToken(String token){
        return decodedToken(token).getSubject();
    }

    private static DecodedJWT decodedToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

}
