package fun.haolo.bigLandlord.db.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.dto.TenantDTO;
import fun.haolo.bigLandlord.db.entity.Tenant;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.mapper.TenantMapper;
import fun.haolo.bigLandlord.db.param.TenantParam;
import fun.haolo.bigLandlord.db.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.vo.TenantOptionsVO;
import fun.haolo.bigLandlord.db.vo.TenantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    IUserService userService;

    @Override
    public Tenant addByVo(TenantParam param, String userName) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(param, tenant, "id");
        tenant.setUserId(userService.getUserIdByUsername(userName));
        boolean b = getBaseMapper().insert(tenant) != 0;
        return b ? tenant : null;
    }

    @Override
    public Boolean remove(Long id, String userName) {
        Tenant tenant = getById(id);
        if (!userService.getUserIdByUsername(userName).equals(tenant.getUserId())) throw new UnauthorizedException("这不是你的租客，无法完成此操作");
        return removeById(tenant);
    }

    @Override
    public Tenant updateByVo(TenantParam param, String userName) {
        Tenant tenant = getById(param.getId());
        if (!userService.getUserIdByUsername(userName).equals(tenant.getUserId())) throw new UnauthorizedException("这不是你的租客，无法完成此操作");
        if (param.getIdcard().isEmpty()) {
            BeanUtils.copyProperties(param, tenant, "id", "idcard");
        } else {
            if (IdcardUtil.isValidCard18(param.getIdcard())) throw new RuntimeException("身份证格式不正确");
            BeanUtils.copyProperties(param, tenant, "id");
        }
        boolean b = updateById(tenant);
        return b ? tenant : null;
    }

    @Override
    public TenantVO getListToVo(String userName, boolean desc, long current, long size) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userService.getUserIdByUsername(userName));
        if (desc) queryWrapper.orderByDesc("id");
        return getListByWrapperToVo(queryWrapper, current, size);
    }

    @Override
    public TenantVO getByNameToVo(String name, String userName, long current, long size) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userService.getUserIdByUsername(userName));
        queryWrapper.like("name", name);
        return getListByWrapperToVo(queryWrapper, current, size);
    }

    @Override
    public List<TenantOptionsVO> getByNameToOptionsVO(String name, String userName) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userService.getUserIdByUsername(userName));
        queryWrapper.like("name", name);
        List<Tenant> tenantList = getBaseMapper().selectPage(new Page<>(1, 10), queryWrapper).getRecords();
        List<TenantOptionsVO> list = new ArrayList<>();
        for (Tenant tenant : tenantList) {
            TenantOptionsVO tenantOptionsVO = new TenantOptionsVO();
            tenantOptionsVO.setTenantId(tenant.getId());
            tenantOptionsVO.setName(tenant.getName());
            tenantOptionsVO.setMobile(tenant.getMobile());
            list.add(tenantOptionsVO);
        }
        return list;
    }

    @Override
    public String getNameById(Long id) {
        return getById(id).getName();
    }

    @Override
    public TenantDTO getById(String userName, Long id) {
        Tenant tenant = getById(id);
        if (!userService.getUserIdByUsername(userName).equals(tenant.getUserId())) throw new UnauthorizedException("这不是你的租客，无法完成此操作");
        TenantDTO tenantDTO = new TenantDTO();
        BeanUtils.copyProperties(tenant, tenantDTO);
        tenantDTO.setIdcard(DesensitizedUtil.idCardNum(tenant.getIdcard(), 2, 3));
        return tenantDTO;
    }

    @Override
    public Long getIdByPhoneNumber(String number) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", number);
        return getOne(wrapper).getId();
    }

    @Override
    public void checkPhone(String phone) {
        QueryWrapper<Tenant> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        if (count(wrapper) == 0) throw new RuntimeException("手机号不存在");
    }

    private TenantVO getListByWrapperToVo(QueryWrapper<Tenant> wrapper, long current, long size) {
        ArrayList<TenantDTO> list = new ArrayList<>();
        Page<Tenant> tenantPage = getBaseMapper().selectPage(new Page<>(current, size), wrapper);
        long total = tenantPage.getTotal();
        List<Tenant> records = tenantPage.getRecords();
        for (Tenant record : records) {
            TenantDTO tenantDTO = new TenantDTO();
            tenantDTO.setId(record.getId());
            tenantDTO.setName(record.getName());
//            tenantDTO.setIdcard(DesensitizedUtil.idCardNum(record.getIdcard(), 2, 3));
            tenantDTO.setIdcard(record.getIdcard());
            tenantDTO.setMobile(record.getMobile());
            tenantDTO.setCreatTime(record.getCreateTime());
            list.add(tenantDTO);
        }
        TenantVO vo = new TenantVO();
        vo.setList(list);
        vo.setTotal(total);
        return vo;
    }
}
