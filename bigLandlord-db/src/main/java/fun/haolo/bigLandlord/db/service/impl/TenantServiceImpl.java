package fun.haolo.bigLandlord.db.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.Tenant;
import fun.haolo.bigLandlord.db.mapper.TenantMapper;
import fun.haolo.bigLandlord.db.param.TenantParam;
import fun.haolo.bigLandlord.db.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IUserService;
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
        Assert.state(userService.getUserIdByUsername(userName).equals(tenant.getUserId()), "这不是你的租客，无法完成此操作");
        return removeById(tenant);
    }

    @Override
    public Tenant updateByVo(TenantParam param, String userName) {
        Tenant tenant = getById(param.getId());
        Assert.state(userService.getUserIdByUsername(userName).equals(tenant.getUserId()), "这不是你的租客，无法完成此操作");
        BeanUtils.copyProperties(param, tenant, "id");
        boolean b = updateById(tenant);
        return b ? tenant : null;
    }

    @Override
    public ArrayList<TenantParam> getListToVo(String userName, Integer page, Integer count) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userService.getUserIdByUsername(userName));
        return getListByWrapperToVo(queryWrapper, page, count);
    }

    @Override
    public ArrayList<TenantParam> getByNameToVo(String name, String userName, Integer page, Integer count) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userService.getUserIdByUsername(userName));
        queryWrapper.eq("name", name);
        return getListByWrapperToVo(queryWrapper, page, count);
    }

    @Override
    public String getNameById(Long id) {
        return getById(id).getName();
    }

    private ArrayList<TenantParam> getListByWrapperToVo(QueryWrapper<Tenant> wrapper, Integer page, Integer count) {
        ArrayList<TenantParam> list = new ArrayList<>();
        TenantParam tenantParam = new TenantParam();
        List<Tenant> records = getBaseMapper().selectPage(new Page<>(page, count), wrapper).getRecords();
        for (Tenant record : records) {
            tenantParam.setId(record.getId());
            tenantParam.setName(record.getName());
            tenantParam.setIdcard(DesensitizedUtil.idCardNum(record.getIdcard(), 2, 3));
            tenantParam.setMobile(record.getMobile());
            list.add(tenantParam);
        }
        return list;
    }
}
