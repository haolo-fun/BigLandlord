package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.param.HouseParam;
import fun.haolo.bigLandlord.db.service.IHouseService;
import fun.haolo.bigLandlord.db.vo.HouseOptionsVO;
import fun.haolo.bigLandlord.db.vo.HouseVO;
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
@RestController
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

    @GetMapping("/list/{current}/{size}")
    @ApiOperation(value = "查询所有信息")
    public ResponseResult<HouseVO> list(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HouseVO houseVO = houseService.listByUsername2VO(userDetails.getUsername(), current, size);
        return ResponseResult.success(houseVO);
    }

    @GetMapping("/list/address/{address}/{current}/{size}")
    @ApiOperation(value = "根据地址查询")
    public ResponseResult<HouseVO> listByAddress(@PathVariable String address, @PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HouseVO houseVO = houseService.listByAddress2VO(address, userDetails.getUsername(), current, size);
        return ResponseResult.success(houseVO);
    }

    @GetMapping("/list/area/{low}/{high}/{current}/{size}")
    @ApiOperation(value = "通过面积范围查询")
    public ResponseResult<HouseVO> listByAreaRange(@PathVariable Integer low, @PathVariable Integer high,
                                                   @PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HouseVO houseVO = houseService.listByAreaRange2VO(low, high, userDetails.getUsername(), current, size);
        return ResponseResult.success(houseVO);
    }

    @GetMapping("/list/price/{low}/{high}/{current}/{size}")
    @ApiOperation(value = "通过价格范围查询")
    public ResponseResult<HouseVO> listByPriceRange(@PathVariable Integer low, @PathVariable Integer high,
                                                    @PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HouseVO houseVO = houseService.listByPriceRange2VO(low, high, userDetails.getUsername(), current, size);
        return ResponseResult.success(houseVO);
    }

    @GetMapping("/list/status/{status}/{current}/{size}")
    @ApiOperation(value = "通过状态查询")
    public ResponseResult<HouseVO> listByStatus(@PathVariable Integer status,
                                                @PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HouseVO houseVO = houseService.listByStatus2VO(status, userDetails.getUsername(), current, size);
        return ResponseResult.success(houseVO);
    }

    @GetMapping("/options/{address}")
    @ApiOperation(value = "获取HouseOptions")
    public ResponseResult<List<HouseOptionsVO>> listByOptions(@PathVariable String address) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<HouseOptionsVO> houseOptions = houseService.getHouseOptions(userDetails.getUsername(), address);
        return ResponseResult.success(houseOptions);
    }
}
