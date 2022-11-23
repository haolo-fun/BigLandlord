package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.Deposit;
import fun.haolo.bigLandlord.db.entity.Tenant;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.mapper.DepositMapper;
import fun.haolo.bigLandlord.db.service.IDepositService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.DepositStatusConstant;
import fun.haolo.bigLandlord.db.utils.SNUtil;
import fun.haolo.bigLandlord.db.dto.DepositDTO;
import fun.haolo.bigLandlord.db.vo.DepositVO;
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
 * @since 2022-10-18
 */
@Service
public class DepositServiceImpl extends ServiceImpl<DepositMapper, Deposit> implements IDepositService {

    @Autowired
    private SNUtil snUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITenantService tenantService;

    @Override
    public Deposit getBySn(String sn) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("deposit_sn", sn);
        return getOne(wrapper);
    }

    @Override
    public DepositVO getBySnToVO(String username, String sn) {
        Long userId = userService.getUserIdByUsername(username);
        DepositVO depositVO = new DepositVO();
        List<DepositDTO> list = new ArrayList<>();
        Deposit deposit = getBySn(sn);
        if (deposit == null) return depositVO;
        if (!deposit.getUserId().equals(userId)) return depositVO;
        DepositDTO depositDTO = new DepositDTO();
        BeanUtils.copyProperties(deposit, depositDTO);
        depositDTO.setName(tenantService.getNameById(depositDTO.getTenantId()));
        list.add(depositDTO);
        depositVO.setList(list);
        depositVO.setTotal(1L);
        return depositVO;
    }

    @Override
    public boolean initAdd(Long userid, Long tenantId, BigDecimal price) {
        Deposit deposit = new Deposit();
        deposit.setUserId(userid);
        deposit.setDepositSn(snUtil.generateDepositSn());
        deposit.setTenantId(tenantId);
        deposit.setDeposit(price);
        deposit.setStatus(DepositStatusConstant.DID_NOT_PAY);
        return save(deposit);
    }

    @Override
    public void delBySn(String username, String sn) {
        Long userId = userService.getUserIdByUsername(username);
        Deposit deposit = getBySn(sn);
        if (deposit == null) throw new RuntimeException("押金单状态异常");
        if (!deposit.getUserId().equals(userId)) throw new UnauthorizedException("不是你的押金单，无法完成此操作");
        removeById(deposit);
    }

    @Override
    public void updateStatus(String username, String sn, Integer depositStatusConstant) {
        Long userId = userService.getUserIdByUsername(username);
        Deposit deposit = getBySn(sn);
        if (deposit == null) throw new RuntimeException("押金单状态异常");
        if (!deposit.getUserId().equals(userId)) throw new UnauthorizedException("不是你的押金单，无法完成此操作");
        Deposit newDeposit = new Deposit();
        BeanUtils.copyProperties(deposit, newDeposit, "updateTime");
        newDeposit.setStatus(depositStatusConstant);
        updateById(newDeposit);
    }

    @Override
    public DepositVO AllList(String username, long current, long size, Boolean desc) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userService.getUserIdByUsername(username));
        if (desc) wrapper.orderByDesc("id");
        return QueryListByWrapper2VO(current, size, wrapper);
    }

    @Override
    public DepositVO listByStatus(String username, Integer depositStatusConstant, long current, long size, Boolean desc) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userService.getUserIdByUsername(username));
        wrapper.eq("status", depositStatusConstant);
        if (desc) wrapper.orderByDesc("id");
        return QueryListByWrapper2VO(current, size, wrapper);
    }

    @Override
    public DepositVO listByTenantId(String username, Long tenantId, long current, long size, Boolean desc) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userService.getUserIdByUsername(username));
        wrapper.eq("tenant_id", tenantId);
        if (desc) wrapper.orderByDesc("id");
        return QueryListByWrapper2VO(current, size, wrapper);
    }

    @Override
    public List<DepositDTO> oneByTenantId(Long tenantId){
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("tenant_id", tenantId);
        wrapper.orderByDesc("id");
        DepositVO depositVO = QueryListByWrapper2VO(1, 1, wrapper);
        return depositVO.getList();
    }

    private DepositVO QueryListByWrapper2VO(long current, long size, QueryWrapper<Deposit> wrapper) {
        Page<Deposit> depositPage = getBaseMapper().selectPage(new Page<>(current, size), wrapper);
        long total = depositPage.getTotal();
        List<Deposit> deposits = depositPage.getRecords();
        ArrayList<DepositDTO> depositDTOS = new ArrayList<>();
        for (Deposit deposit : deposits) {
            DepositDTO depositDTO = new DepositDTO();
            depositDTO.setDepositSn(deposit.getDepositSn());
            depositDTO.setTenantId(deposit.getTenantId());
            depositDTO.setDeposit(deposit.getDeposit());
            depositDTO.setStatus(deposit.getStatus());
            depositDTO.setPayId(deposit.getPayId());
            depositDTO.setPayTime(deposit.getPayTime());
            depositDTO.setCreateTime(deposit.getCreateTime());
            depositDTO.setName(tenantService.getById(depositDTO.getTenantId()).getName());
            depositDTOS.add(depositDTO);
        }
        DepositVO depositVO = new DepositVO();
        depositVO.setList(depositDTOS);
        depositVO.setTotal(total);
        return depositVO;
    }
}
