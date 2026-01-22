package com.lin.login.utils;

import cn.hutool.core.lang.UUID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * JWT⼯具类
 */
public class JwtUtil {
    //有效期为
    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 *1000 ⼀个⼩时
    //设置秘钥明⽂
    public static final String JWT_KEY = "xxx";

    /**
     * 创建token
     *
     * @param username ⽤户名
     * @return
     */
    public static String createJWT(String username) {
        JwtBuilder builder = getJwtBuilder(username, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 解析token
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    private static JwtBuilder getJwtBuilder(String username, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid) //唯⼀的ID
                .setSubject(username) //⽤户名
                .setIssuedAt(now) // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使⽤HS256对称加密算法签名, 第⼆个参数为秘钥
                .setExpiration(expDate); //过期时间
    }

    /**
     * ⽣成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}