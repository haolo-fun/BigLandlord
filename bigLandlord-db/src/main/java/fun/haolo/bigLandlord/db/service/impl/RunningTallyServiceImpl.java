package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.Finance;
import fun.haolo.bigLandlord.db.entity.RunningTally;
import fun.haolo.bigLandlord.db.mapper.RunningTallyMapper;
import fun.haolo.bigLandlord.db.service.IFinanceService;
import fun.haolo.bigLandlord.db.service.IRunningTallyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.dto.RunningTallyDTO;
import fun.haolo.bigLandlord.db.vo.RunningTallyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
@Service
public class RunningTallyServiceImpl extends ServiceImpl<RunningTallyMapper, RunningTally> implements IRunningTallyService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFinanceService financeService;

    @Override
    public RunningTallyVO getAll(String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<RunningTally> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("id");
        return toVO(wrapper, current, size);
    }

    @Override
    public List<RunningTallyDTO> getTen(String username) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<RunningTally> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("id");
        Page<RunningTally> runningTallyPage = getBaseMapper().selectPage(new Page<>(1, 10), wrapper);
        return toDTO(runningTallyPage.getRecords());
    }

    @Override
    public RunningTallyVO getByDeposit(String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<RunningTally> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("type", 0);
        wrapper.orderByDesc("id");
        return toVO(wrapper, current, size);
    }

    @Override
    public RunningTallyVO getByRent(String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<RunningTally> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("type", 1);
        wrapper.orderByDesc("id");
        return toVO(wrapper, current, size);
    }

    @Override
    public void saveDeposit(long userId, String sn, String price) {
        save(userId, 0, sn, price);
    }

    @Override
    public void saveRent(long userId, String sn, String price) {
        save(userId, 1, sn, price);
    }

    private List<RunningTallyDTO> toDTO(List<RunningTally> list) {
        List<RunningTallyDTO> listDto = new ArrayList<>();
        for (RunningTally record : list) {
            RunningTallyDTO runningTallyDTO = new RunningTallyDTO();
            BeanUtils.copyProperties(record, runningTallyDTO);
            listDto.add(runningTallyDTO);
        }
        return listDto;
    }

    private RunningTallyVO toVO(QueryWrapper<RunningTally> wrapper, long current, long size) {
        Page<RunningTally> runningTallyPage = getBaseMapper().selectPage(new Page<>(current, size), wrapper);
        RunningTallyVO runningTallyVO = new RunningTallyVO();
        runningTallyVO.setList(toDTO(runningTallyPage.getRecords()));
        runningTallyVO.setTotal(runningTallyPage.getTotal());
        return runningTallyVO;
    }

    private void save(long userId, Integer type, String sn, String price) {
        BigDecimal priceBigDecimal = new BigDecimal(price);
        RunningTally runningTally = new RunningTally();
        // 获取最后一条记录得到旧余额
        QueryWrapper<RunningTally> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("id");
        RunningTally old = getOne(wrapper);
        // 根据旧余额计算当前余额
        BigDecimal newBalance = priceBigDecimal.add(old == null ? new BigDecimal(0) : old.getBalance());
        // 添加至流水
        runningTally.setUserId(userId);
        runningTally.setSn(sn);
        runningTally.setType(type);
        runningTally.setPrice(priceBigDecimal);
        runningTally.setBalance(newBalance);
        boolean Dignum = priceBigDecimal.signum() == -1;
        runningTally.setForm(Dignum ? "out" : "in");
        save(runningTally);
        // 更新finance数据
        Finance finance = financeService.getById(userId);
        if (type == 0){
            if (Dignum) {
                finance.setDeposit(finance.getDeposit().add(priceBigDecimal));
            } else {
                finance.setDeposit(finance.getDeposit().subtract(priceBigDecimal));
            }
        }else {
            if (Dignum) {
                finance.setRent(finance.getRent().add(priceBigDecimal));
            } else {
                finance.setRent(finance.getRent().subtract(priceBigDecimal));
            }
        }
        financeService.updateById(finance);
    }
}
