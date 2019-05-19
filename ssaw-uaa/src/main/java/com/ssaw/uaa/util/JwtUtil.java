package com.ssaw.uaa.util;

import com.alibaba.fastjson.JSON;
import com.ssaw.ssawauthenticatecenterfeign.vo.user.UserInfoVO;
import com.ssaw.uaa.exception.BaseTokenException;
import com.ssaw.uaa.exception.TokenErrorException;
import com.ssaw.uaa.exception.TokenExpireException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Jwt工具类
 * @author HuSen
 * @date 2019/4/27 14:13
 */
public class JwtUtil {

    private static final String SECRET = "521428Slyp";

    public static final String HEADER_AUTH = "Authorization";

    public static final String BEARER = "Bearer";

    private static final long ONLINE_TIME = 86400000;

    public static String generateToken(UserInfoVO user) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("user", user);
        map.put("timestamp", System.currentTimeMillis());
        return Jwts.builder()
                .setSubject("userInfo").setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static UserInfoVO validateToken(String token) throws BaseTokenException {
        if (token != null) {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            Object timestamp = body.get("timestamp");
            if (Objects.isNull(timestamp)) {
                throw new TokenErrorException("token is error");
            }
            long timestampL = (long) timestamp;
            if (timestampL + ONLINE_TIME < System.currentTimeMillis()) {
                throw new TokenExpireException("token is expire");
            }
            return JSON.parseObject(JSON.toJSONString(body.get("user")), UserInfoVO.class);
        } else {
            throw new TokenErrorException("token is error, please check");
        }
    }
}