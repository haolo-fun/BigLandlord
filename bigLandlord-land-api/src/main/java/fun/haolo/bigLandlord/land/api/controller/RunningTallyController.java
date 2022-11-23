package fun.haolo.bigLandlord.land.api.controller;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.dto.RunningTallyDTO;
import fun.haolo.bigLandlord.db.service.IRunningTallyService;
import fun.haolo.bigLandlord.db.vo.RunningTallyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/runningTally")
@Api(tags = "land_资金流水接口")
public class RunningTallyController {
    @Autowired
    private IRunningTallyService runningTallyService;

    @GetMapping("/all/{current}/{size}")
    @ApiOperation(value = "获取所有流水")
    public ResponseResult<RunningTallyVO> getAll(@PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RunningTallyVO vo = runningTallyService.getAll(userDetails.getUsername(), current, size);
        return ResponseResult.success(vo);
    }

    @GetMapping("/recently")
    @ApiOperation(value = "获取最近十条流水")
    public ResponseResult<List<RunningTallyDTO>> getTen() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RunningTallyDTO> list = runningTallyService.getTen(userDetails.getUsername());
        return ResponseResult.success(list);
    }

    @GetMapping("/deposit/{current}/{size}")
    @ApiOperation(value = "获取押金流水")
    public ResponseResult<RunningTallyVO> getByDeposit(@PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RunningTallyVO vo = runningTallyService.getByDeposit(userDetails.getUsername(), current, size);
        return ResponseResult.success(vo);
    }

    @GetMapping("/rent/{current}/{size}")
    @ApiOperation(value = "获取租金流水")
    public ResponseResult<RunningTallyVO> getByRent(@PathVariable long current, @PathVariable long size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RunningTallyVO vo = runningTallyService.getByRent(userDetails.getUsername(), current, size);
        return ResponseResult.success(vo);
    }
}
