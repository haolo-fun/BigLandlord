package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.entity.Tenant;
import fun.haolo.bigLandlord.db.param.TenantParam;
import fun.haolo.bigLandlord.db.service.ITenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@RestController
@RequestMapping("/tenant")
@Api(tags = "land_租户控制接口")
public class TenantController {

    @Autowired
    ITenantService tenantService;

    @PostMapping("/add")
    @ApiOperation(value = "添加租户")
    public ResponseResult<Tenant> add(TenantParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Tenant tenant = tenantService.addByVo(param, username);
        return tenant != null ? ResponseResult.success(tenant) : ResponseResult.failed();
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除租户")
    public ResponseResult<Object> delete(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        return tenantService.remove(id, username) ? ResponseResult.success() : ResponseResult.failed();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新租户信息")
    public ResponseResult<Tenant> update(TenantParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Tenant tenant = tenantService.updateByVo(param, username);
        return tenant != null ? ResponseResult.success(tenant) : ResponseResult.failed();
    }

    @GetMapping("/get/{page}/{count}")
    @ApiOperation(value = "查询所有租户信息")
    public ResponseResult<ArrayList<TenantParam>> selectAll(@PathVariable Integer page, @PathVariable Integer count) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        ArrayList<TenantParam> list = tenantService.getListToVo(username, page, count);
        return ResponseResult.success(list);
    }

    @GetMapping({"/get/{name}/{page}/{count}"})
    @ApiOperation(value = "通过租户姓名查询信息")
    public ResponseResult<ArrayList<TenantParam>> selectAll(@PathVariable String name, @PathVariable Integer page, @PathVariable Integer count) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        ArrayList<TenantParam> list = tenantService.getByNameToVo(name, username, page, count);
        return ResponseResult.success(list);
    }
}
