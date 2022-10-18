package fun.haolo.bigLandlord.core.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import fun.haolo.bigLandlord.core.dto.SecurityUserDetails;
import fun.haolo.bigLandlord.core.service.LoginService;
import fun.haolo.bigLandlord.db.utils.JwtTokenUtil;
import fun.haolo.bigLandlord.db.entity.User;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author haolo
 * @Date 2022-10-13 20:38
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService iUserService;

    private static final Log log = LogFactory.get();

    @Override
    public String login(String username, String password) {
        // 验证信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityUserDetails userDetails = (SecurityUserDetails) authenticate.getPrincipal();
        // 用户信息存入redis
        redisUtil.setCacheObject("login:" + username, userDetails);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public User register(User user) {
        // 查询是否有相同用户名的用户
        User userByUsername = iUserService.getUserByUsername(user.getUsername());
        if (Objects.nonNull(userByUsername)) {
            throw new RuntimeException("该用户名已被注册");
        }
        // todo 手机验证码验证
        // 将密码进行加密操作
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        log.info("注册用户:{}", user.toString());
        // 存入数据库
        return iUserService.save(user) ? user : null;
    }
}
