package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.service.IFinanceService;
import fun.haolo.bigLandlord.db.vo.FinanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/finance")
@Api(tags = "land_财务接口")
public class FinanceController {
    @Autowired
    private IFinanceService financeService;

    @GetMapping("/msg")
    @ApiOperation(value = "获取财务信息")
    public ResponseResult<FinanceVO> getMsg(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FinanceVO msg = financeService.getMsg(userDetails.getUsername());
        return ResponseResult.success(msg);
    }
}
