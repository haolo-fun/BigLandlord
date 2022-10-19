package fun.haolo.bigLandlord.core.component;

import cn.hutool.jwt.JWT;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import fun.haolo.bigLandlord.core.dto.SecurityUserDetails;
import fun.haolo.bigLandlord.core.exception.TokenLogoutException;
import fun.haolo.bigLandlord.db.utils.JwtTokenUtil;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private UserDetailsService userDetailsService;
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
                // 判断token是否合法
                if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null && !jwtTokenUtil.isTokenExpired(jwt)) {
                    // 通过token中的uuid从redis中判断token是否注销
                    String uuid = (String) jwt.getPayload("id");
                    if (Objects.isNull(redisUtil.getCacheObject("token:" + uuid))){
                        throw new TokenLogoutException("登录已被注销，请重新登录");
                    }
                    // 获取userDetails
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
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
