package fun.haolo.bigLandlord.db.service.impl;

import fun.haolo.bigLandlord.db.entity.Finance;
import fun.haolo.bigLandlord.db.entity.RunningTally;
import fun.haolo.bigLandlord.db.mapper.FinanceMapper;
import fun.haolo.bigLandlord.db.service.IFinanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IRunningTallyService;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.vo.FinanceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, Finance> implements IFinanceService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRunningTallyService runningTallyService;

    @Override
    public FinanceVO getMsg(String username) {
        Long userId = userService.getUserIdByUsername(username);
        Finance finance = getById(userId);
        FinanceVO financeVO = new FinanceVO();
        BeanUtils.copyProperties(finance, financeVO);
        return financeVO;
    }

    @Override
    public void init(Long userId) {
        Finance finance = new Finance();
        finance.setUserId(userId);
        finance.setDeposit(new BigDecimal(0));
        finance.setRent(new BigDecimal(0));
        finance.setWithdrawDeposit(new BigDecimal(0));
        finance.setWithdrawRent(new BigDecimal(0));
        if (!save(finance)) throw new RuntimeException("finance can not be init");
        RunningTally runningTally = new RunningTally();
        runningTally.setUserId(userId);
        runningTally.setType(0);
        runningTally.setPrice(new BigDecimal(0));
        runningTally.setBalance(new BigDecimal(0));
        runningTally.setForm("in");
        if (!runningTallyService.save(runningTally)) throw new RuntimeException("finance can not be init");
        runningTally.setType(1);
        if (!runningTallyService.save(runningTally)) throw new RuntimeException("finance can not be init");
    }
}
