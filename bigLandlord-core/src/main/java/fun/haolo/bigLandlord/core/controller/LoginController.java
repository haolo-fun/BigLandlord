package fun.haolo.bigLandlord.core.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.core.param.UserParam;
import fun.haolo.bigLandlord.core.service.LoginService;
import fun.haolo.bigLandlord.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author haolo
 * @Date 2022-10-14 11:16
 * @Description
 */
@RestController
@Api(tags = "core_登录接口")
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletRequest request;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader; //token在请求头中的名字

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public ResponseResult<String> login(String username, String password) {
        String token = loginService.login(username, password);
        return ResponseResult.success(token);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册接口")
    public ResponseResult<User> register(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        User register = loginService.register(user);
        if (register == null) return ResponseResult.failed();
        return ResponseResult.success(register);
    }

    @PostMapping("/exit")
    @ApiOperation(value = "注销登录")
    public ResponseResult<Object> logout() {
        String token = request.getHeader(tokenHeader);
        return loginService.logout(token) ? ResponseResult.success() : ResponseResult.failed();
    }

    @PostMapping("/exitAll")
    @ApiOperation(value = "注销所有登录")
    public ResponseResult<Object> logoutAll() {
        String token = request.getHeader(tokenHeader);
        return loginService.logoutAll(token) ? ResponseResult.success() : ResponseResult.failed();
    }
}
