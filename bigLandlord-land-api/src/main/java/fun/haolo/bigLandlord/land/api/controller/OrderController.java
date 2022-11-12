package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.param.OrderAdditionalParam;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;
import fun.haolo.bigLandlord.db.dto.OrderDTO;
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

    @GetMapping("/additional/{orderSn}")
    @ApiOperation(value = "查询附加信息")
    public ResponseResult<List<OrderAdditionalVO>> getAdditionalList(@PathVariable String orderSn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderAdditionalVO> list = service.getAdditionalList(userDetails.getUsername(), orderSn);
        return ResponseResult.success(list);
    }


    @PostMapping("/additional/{orderSn}")
    @ApiOperation(value = "添加附加信息")
    public ResponseResult<Object> addOrderAdditional(@PathVariable String orderSn, @RequestBody OrderAdditionalParam param) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.addOrderAdditional(userDetails.getUsername(), orderSn, param);
        return ResponseResult.success();
    }

    @PutMapping("/additional")
    @ApiOperation(value = "修改附加信息")
    public ResponseResult<Object> updateOrderAdditional(@RequestBody OrderAdditionalVO orderAdditionalVO) {
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

    @PutMapping("/updateStatus/NOT_ISSUED/{sn}")
    @ApiOperation(value = "更新租单状态")
    public ResponseResult<Object> updateStatusNOT_ISSUED(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean b = service.updateStatus(userDetails.getUsername(), sn, OrderStatusConstant.NOT_ISSUED);
        return b ? ResponseResult.success() : ResponseResult.failed();
    }

    @PutMapping("/updateStatus/HAS_BEEN_ISSUED/{sn}")
    @ApiOperation(value = "更新租单状态")
    public ResponseResult<Object> updateStatusHAS_BEEN_ISSUED(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean b = service.updateStatus(userDetails.getUsername(), sn, OrderStatusConstant.HAS_BEEN_ISSUED);
        return b ? ResponseResult.success() : ResponseResult.failed();
    }

    @PutMapping("/updateStatus/HAVE_TO_PAY/{sn}")
    @ApiOperation(value = "更新租单状态")
    public ResponseResult<Object> updateStatusHAVE_TO_PAY(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean b = service.updateStatus(userDetails.getUsername(), sn, OrderStatusConstant.HAVE_TO_PAY);
        return b ? ResponseResult.success() : ResponseResult.failed();
    }

    @GetMapping("/list/{current}/{size}/{desc}")
    @ApiOperation(value = "查询所有租单信息")
    public ResponseResult<OrderVO> list(@PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderVO orderVO = service.list(userDetails.getUsername(), current, size, desc);
        return ResponseResult.success(orderVO);
    }

    @GetMapping("/listByOrderSn/{sn}")
    @ApiOperation(value = "根据单号查询信息")
    public ResponseResult<OrderVO> getByOrderSn(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderVO orderVO = service.getByOrderSn(userDetails.getUsername(), sn);
        return ResponseResult.success(orderVO);
    }

    @GetMapping("/listByHouseId/{houseId}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据房源id查信息")
    public ResponseResult<OrderVO> listByHouseId(@PathVariable Long houseId, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderVO orderVO = service.listByHouseId(userDetails.getUsername(), houseId, current, size, desc);
        return ResponseResult.success(orderVO);
    }

    @GetMapping("/listByTenantId/{tenantId}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据租客id查信息")
    public ResponseResult<OrderVO> listByTenantId(@PathVariable Long tenantId, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderVO orderVO = service.listByTenantId(userDetails.getUsername(), tenantId, current, size, desc);
        return ResponseResult.success(orderVO);
    }

    @GetMapping("/listByOrderStatus/{orderStatus}/{current}/{size}/{desc}")
    @ApiOperation(value = "根据租单状态查信息")
    public ResponseResult<OrderVO> listByOrderStatus(@PathVariable Integer orderStatus, @PathVariable long current, @PathVariable long size, @PathVariable boolean desc) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderVO orderVO = service.listByOrderStatus(userDetails.getUsername(), orderStatus, current, size, desc);
        return ResponseResult.success(orderVO);
    }


}
