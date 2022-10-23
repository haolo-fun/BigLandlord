package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.param.OrderAdditionalParam;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;
import fun.haolo.bigLandlord.db.vo.OrderVO;
import fun.haolo.bigLandlord.land.api.service.LandOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/order")
@Api(tags = "land_租单控制接口")
public class OrderController {

    @Autowired
    private LandOrderService service;

    @PostMapping("/build/{tenantId}/{houseId}/{count}")
    @ApiOperation(value = "构建租单")
    public ResponseResult<Object> buildOrder(@PathVariable Long tenantId, @PathVariable Long houseId, @PathVariable Short count) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.buildOrder(userDetails.getUsername(), tenantId, houseId, count);
        return ResponseResult.success();
    }

    @PostMapping("/additional/{orderSn}")
    @ApiOperation(value = "添加附加信息")
    public ResponseResult<Object> addOrderAdditional(@PathVariable String orderSn, OrderAdditionalParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.addOrderAdditional(userDetails.getUsername(), orderSn, param);
        return ResponseResult.success();
    }

    @PutMapping("/additional")
    @ApiOperation(value = "修改附加信息")
    public ResponseResult<Object> updateOrderAdditional(OrderAdditionalVO orderAdditionalVO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.updateOrderAdditional(userDetails.getUsername(), orderAdditionalVO);
        return ResponseResult.success();
    }

    @DeleteMapping("/additional/{orderAdditionalId}")
    @ApiOperation(value = "删除附加信息")
    public ResponseResult<Object> delOrderAdditional(@PathVariable Long orderAdditionalId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.delOrderAdditional(userDetails.getUsername(), orderAdditionalId);
        return ResponseResult.success();
    }

    @PutMapping("/updateStatus/{sn}/{status}")
    @ApiOperation(value = "更新租单状态")
    public ResponseResult<Object> updateStatus(@PathVariable String sn, @PathVariable Integer status) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean b = service.updateStatus(userDetails.getUsername(), sn, status);
        return b ? ResponseResult.success() : ResponseResult.failed();
    }

    @GetMapping("/list/{current}/{size}/{desc}")
    @ApiOperation(value = "查询所有租单信息")
    public ResponseResult<List<OrderVO>> list(@PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderVO> list = service.list(userDetails.getUsername(), current, size, desc);
        return ResponseResult.success(list);
    }

    @GetMapping("/listByOrderSn/{sn}")
    @ApiOperation(value = "根据单号查询信息")
    public ResponseResult<List<OrderVO>> getByOrderSn(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderVO> list = service.getByOrderSn(userDetails.getUsername(), sn);
        return ResponseResult.success(list);
    }

    @GetMapping("/listByHouseId/{houseId}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据房源id查信息")
    public ResponseResult<List<OrderVO>> listByHouseId(@PathVariable Long houseId, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderVO> list = service.listByHouseId(userDetails.getUsername(), houseId, current, size, desc);
        return ResponseResult.success(list);
    }

    @GetMapping("/listByTenantId/{tenantId}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据租客id查信息")
    public ResponseResult<List<OrderVO>> listByTenantId(@PathVariable Long tenantId, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderVO> list = service.listByTenantId(userDetails.getUsername(), tenantId, current, size, desc);
        return ResponseResult.success(list);
    }

    @GetMapping("/listByOrderStatus/{orderStatus}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据租单状态查信息")
    public ResponseResult<List<OrderVO>> listByOrderStatus(@PathVariable Integer orderStatus, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderVO> list = service.listByOrderStatus(userDetails.getUsername(), orderStatus, current, size, desc);
        return ResponseResult.success(list);
    }


}
