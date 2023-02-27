package fun.haolo.bigLandlord.core.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import fun.haolo.bigLandlord.core.dto.SecurityUserDetails;
import fun.haolo.bigLandlord.core.param.ResetPassWordParam;
import fun.haolo.bigLandlord.core.service.LoginService;
import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import fun.haolo.bigLandlord.db.service.IFinanceService;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import fun.haolo.bigLandlord.db.utils.JwtTokenUtil;
import fun.haolo.bigLandlord.db.entity.User;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import fun.haolo.bigLandlord.db.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private IUserRoleRelationService userRoleRelationService;

    @Autowired
    private IFinanceService financeService;

    @Value("${jwt.expiration}")
    private Integer expiration; //单位：秒

    private static final Log log = LogFactory.get();

    @Override
    public String login(String username, String password) {
        // 验证信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        // tokenId信息存入redis
        String uuid = jwtTokenUtil.getUUIDByToken(token);
        redisUtil.setCacheObject("token:" + username + ":" + uuid, userDetails, expiration, TimeUnit.SECONDS); // 过期时间单位，秒
        return token;
    }

    @Override
    @Transactional
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
        if (!iUserService.save(user)) throw new RuntimeException("注册失败");
        // todo 添加角色信息
        // 财务初始化
        financeService.init(user.getId());
        return user;
    }

    @Override
    public Boolean logout(String token) {
        JWT jwt = jwtTokenUtil.getJWTFromToken(token);
        String id = (String) jwt.getPayload("id");
        String username = (String) jwt.getPayload("sub");
        return redisUtil.deleteObject("token:" + username + ":" + id);
    }

    @Override
    public Boolean logoutAll(String token) {
        JWT jwt = jwtTokenUtil.getJWTFromToken(token);
        String username = (String) jwt.getPayload("sub");
        return redisUtil.deleteObjectsByPattern("token:" + username + ":*");
    }

    @Override
    public UserInfoVO userInfo(String username) {
        User user = iUserService.getUserByUsername(username);
        List<String> roles = userRoleRelationService.getRoles(user.getId());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setIcon(user.getIcon());
        userInfoVO.setNickName(user.getNickName());
        userInfoVO.setMobile(DesensitizedUtil.mobilePhone(user.getMobile()));
        userInfoVO.setRoles(roles);
        return userInfoVO;
    }

    @Override
    public void resetPassword(String username, ResetPassWordParam resetPassWordParam) {
        User user = iUserService.getUserByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(resetPassWordParam.getOldPw(), user.getPassword());
        if (!matches) throw new RuntimeException("密码错误，若忘记密码请联系管理员");
        iUserService.resetPassword(username, resetPassWordParam.getNewPw());
    }
}
