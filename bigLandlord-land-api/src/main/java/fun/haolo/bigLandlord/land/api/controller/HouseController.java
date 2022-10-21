package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.param.HouseParam;
import fun.haolo.bigLandlord.db.service.IHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@Controller
@Api(tags = "land_房源控制接口")
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private IHouseService houseService;

    @PostMapping("/add")
    @ApiOperation(value = "添加房源")
    public ResponseResult<House> add(HouseParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        House house = houseService.add(param, userDetails.getUsername());
        return house != null ? ResponseResult.success(house) : ResponseResult.failed();
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除房源")
    public ResponseResult<Object> del(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean del = houseService.del(id, userDetails.getUsername());
        return del ? ResponseResult.success() : ResponseResult.failed();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新房源信息")
    public ResponseResult<House> update(HouseParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        House house = houseService.update(param, userDetails.getUsername());
        return house != null ? ResponseResult.success(house) : ResponseResult.failed();
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有信息")
    public ResponseResult<List<HouseParam>> list() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HouseParam> list = houseService.listByUsername2VO(userDetails.getUsername());
        return ResponseResult.success(list);
    }

    @GetMapping("/list/area/{low}/{high}")
    @ApiOperation(value = "通过面积范围查询")
    public ResponseResult<List<HouseParam>> listByAreaRange(@PathVariable Integer low, @PathVariable Integer high) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HouseParam> list = houseService.listByAreaRange2VO(low, high, userDetails.getUsername());
        return ResponseResult.success(list);
    }

    @GetMapping("/list/price/{low}/{high}")
    @ApiOperation(value = "通过价格范围查询")
    public ResponseResult<List<HouseParam>> listByPriceRange(@PathVariable Integer low, @PathVariable Integer high) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HouseParam> list = houseService.listByPriceRange2VO(low, high, userDetails.getUsername());
        return ResponseResult.success(list);
    }

    @GetMapping("/list/status/{status}")
    @ApiOperation(value = "通过状态查询")
    public ResponseResult<List<HouseParam>> listByStatus(@PathVariable Integer status) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HouseParam> list = houseService.listByStatus2VO(status, userDetails.getUsername());
        return ResponseResult.success(list);
    }
}
