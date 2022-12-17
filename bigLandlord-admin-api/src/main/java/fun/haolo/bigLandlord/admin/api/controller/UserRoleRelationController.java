package fun.haolo.bigLandlord.admin.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import fun.haolo.bigLandlord.db.vo.RoleRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-10-13
 */
@RestController
@Api(tags = "admin_用户角色控制接口")
@RequestMapping("/admin/roleRelation")
public class UserRoleRelationController {

    @Autowired
    private IUserRoleRelationService userRoleRelationService;

    @GetMapping("/list/{username}")
    @ApiOperation(value = "获取用户角色信息")
    public ResponseResult<List<RoleRelationVO>> list(@PathVariable String username) {
        List<RoleRelationVO> list = userRoleRelationService.getList(username);
        return ResponseResult.success(list);
    }

    @PostMapping("/{username}/{roleName}")
    @ApiOperation(value = "添加角色")
    public ResponseResult<Object> addRole(@PathVariable String username, @PathVariable String roleName) {
        userRoleRelationService.addRole(username, roleName);
        return ResponseResult.success();
    }

    @DeleteMapping("/{username}/{roleName}")
    @ApiOperation(value = "删除角色")
    public ResponseResult<Object> delRole(@PathVariable String username, @PathVariable String roleName) {
        userRoleRelationService.delRole(username, roleName);
        return ResponseResult.success();
    }
}
