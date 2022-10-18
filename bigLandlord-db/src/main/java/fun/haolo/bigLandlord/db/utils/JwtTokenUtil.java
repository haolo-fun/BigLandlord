package fun.haolo.bigLandlord.db.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author haolo
 * @Date 2022-10-12 13:14
 * @Description jwt生成token的工具类
 */
@Component
public class JwtTokenUtil {
    private static final Log log = LogFactory.get();

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration; //单位：秒

    /**
     * 生成JWT的token
     */
    public String generateToken(Map<String, Object> payload) {
        payload.put("exp", generateExpirationDate());
        payload.put("id", getUUID());
        return JWTUtil.createToken(payload, secret.getBytes());
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        payload.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(payload);
    }

    /**
     * 从token中获取JWT中的负载
     */
    public JWT getJWTFromToken(String token) {
        if (getTokenVerify(token)) {
            return JWTUtil.parseToken(token);
        }
        log.info("JWT格式验证失败:{}", token);
        return null;
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(JWT jwt) {
        if (jwt == null) return true;
        Date expiredDate = (Date) jwt.getPayload("exp");
        return expiredDate.before(new Date());
    }

    /**
     * 验证token的格式有效性
     */
    private boolean getTokenVerify(String token) {
        return JWTUtil.verify(token, secret.getBytes());
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

}
