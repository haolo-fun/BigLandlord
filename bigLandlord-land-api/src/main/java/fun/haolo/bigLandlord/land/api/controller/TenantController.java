package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.dto.TenantDTO;
import fun.haolo.bigLandlord.db.entity.Tenant;
import fun.haolo.bigLandlord.db.param.TenantParam;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.vo.TenantOptionsVO;
import fun.haolo.bigLandlord.db.vo.TenantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseResult<Tenant> add(@RequestBody TenantParam param) {
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
    public ResponseResult<Tenant> update(@RequestBody TenantParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Tenant tenant = tenantService.updateByVo(param, username);
        return tenant != null ? ResponseResult.success(tenant) : ResponseResult.failed();
    }

    @GetMapping("/get/desc/{current}/{size}")
    @ApiOperation(value = "查询所有租户信息")
    public ResponseResult<TenantVO> selectAllDesc(@PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        TenantVO vo = tenantService.getListToVo(username, true, current, size);
        return ResponseResult.success(vo);
    }

    @GetMapping("/get/{current}/{size}")
    @ApiOperation(value = "查询所有租户信息")
    public ResponseResult<TenantVO> selectAll(@PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        TenantVO vo = tenantService.getListToVo(username, false, current, size);
        return ResponseResult.success(vo);
    }

    @GetMapping({"/get/{name}/{current}/{size}"})
    @ApiOperation(value = "通过租户姓名查询信息")
    public ResponseResult<TenantVO> selectAll(@PathVariable String name, @PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        TenantVO vo = tenantService.getByNameToVo(name, username, current, size);
        return ResponseResult.success(vo);
    }

    @GetMapping("/get/el-select/{name}")
    @ApiOperation(value = "通过租户姓名查询信息(el-select)")
    public ResponseResult<List<TenantOptionsVO>> elSelect(@PathVariable String name) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TenantOptionsVO> list = tenantService.getByNameToOptionsVO(name, userDetails.getUsername());
        return ResponseResult.success(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public ResponseResult<TenantDTO> getById(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TenantDTO tenantDTO = tenantService.getById(userDetails.getUsername(), id);
        return ResponseResult.success(tenantDTO);
    }

}
