package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.Deposit;
import fun.haolo.bigLandlord.db.mapper.DepositMapper;
import fun.haolo.bigLandlord.db.service.IDepositService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@Service
public class DepositServiceImpl extends ServiceImpl<DepositMapper, Deposit> implements IDepositService {

    @Override
    public Deposit getBySn(String sn) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("deposit_sn",sn);
        return getOne(wrapper);
    }
}
