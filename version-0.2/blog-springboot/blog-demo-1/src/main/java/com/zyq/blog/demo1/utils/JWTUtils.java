package com.zyq.blog.demo1.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyq.blog.demo1.exception.BlogException;
import com.zyq.blog.demo1.model.dto.UserDTO;
import com.zyq.blog.demo1.model.enums.ErrorInfoEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtils {
    /**
     * 密钥
     */
    private static final String SECRET = "my_secret";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 60 * 60 * 12L;//单位为秒

    /**
     * 签发者
     */
    private static final String ISSUER = "hikari";

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(UserDTO userDTO) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
//                //可以将基本信息放到claims中
                .withClaim("username", userDTO.getUsername())//userId
                .withClaim("password", userDTO.getPassword())//password
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .withIssuer(ISSUER) // 签发者
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    private static DecodedJWT decodedToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (TokenExpiredException e) {
            throw new BlogException(ErrorInfoEnum.TOKEN_EXPIRED);
        }
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        //decodedJWT.getClaim("属性").asString()  获取负载中的属性值
        return decodedToken(token).getClaims();
    }
    /**
     *  校验token时间，它自己校验不对会抛错不用我管
     */
//    public static Boolean isExpired(String token){
//        Date expiresAt = decodedToken(token).getExpiresAt();
//        return expiresAt.before(new Date());
//    }
}
