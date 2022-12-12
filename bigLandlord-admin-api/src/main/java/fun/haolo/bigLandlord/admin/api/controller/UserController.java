package fun.haolo.bigLandlord.admin.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.param.UserParam;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.vo.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author haolo
 * @since 2022-12-11 11:51
 */
@RestController
@Api(tags = "admin_用户控制接口")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/list/{current}/{size}")
    public ResponseResult<UserVO> list(@PathVariable long current, @PathVariable long size) {
        UserVO userVO = iUserService.getListToVoByAdmin(current, size);
        return ResponseResult.success(userVO);
    }

    @GetMapping("/getOne/{username}")
    public ResponseResult<UserVO> getOne(@PathVariable String username) {
        UserVO userVO = iUserService.getListByUsernameToVoByAdmin(username);
        return ResponseResult.success(userVO);
    }

    @PutMapping("/resetPassword/{username}")
    public ResponseResult<Object> resetPassword(@PathVariable String username) {
        iUserService.resetPasswordByAdmin(username);
        return ResponseResult.success();
    }

    @PutMapping("/resetStatus/{username}")
    public ResponseResult<Object> resetStatus(@PathVariable String username) {
        iUserService.resetStatusByAdmin(username);
        return ResponseResult.success();
    }

    @PostMapping("/add")
    public ResponseResult<Object> addUser(@RequestBody UserParam userParam) {
        iUserService.addUserByAdmin(userParam);
        return ResponseResult.success();
    }

    @PutMapping("/update")
    public ResponseResult<Object> updateUser(@RequestBody UserParam userParam) {
        iUserService.updateUserByAdmin(userParam);
        return ResponseResult.success();
    }
}
