package fun.haolo.bigLandlord.core.component;

import cn.hutool.jwt.JWT;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import fun.haolo.bigLandlord.core.dto.SecurityUserDetails;
import fun.haolo.bigLandlord.db.utils.JwtTokenUtil;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author haolo
 * @Date 2022-10-13 10:53
 * @Description jwt授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Log log = LogFactory.get();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader; //token在请求头中的名字

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(tokenHeader);
        if (StringUtils.hasText(token)) {
            //解析token
            JWT jwt = jwtTokenUtil.getJWTFromToken(token);
            if (jwt != null) {
                String username = (String) jwt.getPayload("username");
                log.info("checking username:{}", username);
                if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null && !jwtTokenUtil.isTokenExpired(jwt)) {
                    // 从redis中获取用户信息
                    // 如果解析出来的username和redis中的username一致，则继续
                    SecurityUserDetails userDetails = redisUtil.getCacheObject("login:" + username);
                    if (!Objects.isNull(userDetails)) {
                        log.info("userDetails:{}", userDetails);
                        // 存入SecurityContextHolder
                        // 获取权限信息封装到Authentication中
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
