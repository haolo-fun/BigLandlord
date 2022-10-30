package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.service.IDepositService;
import fun.haolo.bigLandlord.db.utils.DepositStatusConstant;
import fun.haolo.bigLandlord.db.vo.DepositVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@RestController
@Api(tags = "land_押金单控制接口")
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private IDepositService depositService;

    @DeleteMapping("/{sn}")
    @ApiOperation(value = "删除押金单")
    private ResponseResult<Object> del(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        depositService.delBySn(userDetails.getUsername(), sn);
        return ResponseResult.success();
    }

    @PutMapping("/status/HAVE_TO_PAY/{sn}")
    @ApiOperation(value = "更新状态为已支付")
    public ResponseResult<Object> updateStatusPay(@PathVariable String sn) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        depositService.updateStatus(userDetails.getUsername(), sn, DepositStatusConstant.HAVE_TO_PAY);
        return ResponseResult.success();
    }

    @GetMapping("/list/{current}/{size}")
    @ApiOperation(value = "获取所有押金单")
    public ResponseResult<DepositVO> list(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.AllList(userDetails.getUsername(), current, size, false);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/desc/{current}/{size}")
    @ApiOperation(value = "获取所有押金单（倒序）")
    public ResponseResult<DepositVO> listByDESC(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.AllList(userDetails.getUsername(), current, size, true);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/DID_NOT_PAY/{current}/{size}")
    @ApiOperation(value = "获取所有未付款押金单")
    public ResponseResult<DepositVO> listByNotPay(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.DID_NOT_PAY, current, size, false);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/DID_NOT_PAY/desc/{current}/{size}")
    @ApiOperation(value = "获取所有未付款押金单（倒序）")
    public ResponseResult<DepositVO> listByNotPayDESC(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.DID_NOT_PAY, current, size, true);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/HAVE_TO_PAY/{current}/{size}")
    @ApiOperation(value = "获取所有已付款押金单")
    public ResponseResult<DepositVO> listByPay(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.HAVE_TO_PAY, current, size, false);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/HAVE_TO_PAY/desc/{current}/{size}")
    @ApiOperation(value = "获取所有已付款押金单（倒序）")
    public ResponseResult<DepositVO> listByPayDESC(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.HAVE_TO_PAY, current, size, true);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/REFUND/{current}/{size}")
    @ApiOperation(value = "获取所有已退款押金单")
    public ResponseResult<DepositVO> listByReFund(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.REFUND, current, size, false);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/REFUND/desc/{current}/{size}")
    @ApiOperation(value = "获取所有已退款押金单（倒序）")
    public ResponseResult<DepositVO> listByReFundDESC(@PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByStatus(userDetails.getUsername(), DepositStatusConstant.REFUND, current, size, true);
        return ResponseResult.success(depositVO);
    }

    @GetMapping("/list/{tenantId}/{current}/{size}")
    @ApiOperation(value = "根据租客id获取所有押金单")
    public ResponseResult<DepositVO> listByTenantId(@PathVariable Long tenantId, @PathVariable Long current, @PathVariable Long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepositVO depositVO = depositService.listByTenantId(userDetails.getUsername(), tenantId, current, size, false);
        return ResponseResult.success(depositVO);
    }

}
