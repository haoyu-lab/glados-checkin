package com.example.gladoscheckin.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {
    // 过期时间 240小时
    private static final long TOKEN_EXPIRE_TIME = 60*60*1000;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 60*1000;
    // 密钥
    private static final String SECRET = "SHIRO+JWT+LmMGStGtOpF4xNyvYt54EQ==";


    /**
     * 生成 token, 10天后过期
     *
     * @param userId 用户id
     * @return 加密的token
     */
    public static String createToken(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("userId",userId)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    /**
     * 生成 token, 10天后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String refreshTokenCreateToken(String username,String providerId) {
        try {
            Date date = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("providerId",providerId)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }



    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成token
     * @return
     */
    public static String createToken() {
        try {
            Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String token = JWTUtil.createToken();
        System.out.println(token);
    }
}
