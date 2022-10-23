package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.param.TenantParam;

import java.util.ArrayList;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface ITenantService extends IService<Tenant> {

    Tenant addByVo(TenantParam param, String userName);

    Boolean remove(Long id, String userName);

    Tenant updateByVo(TenantParam param, String userName);

    ArrayList<TenantParam> getListToVo(String userName, Integer page, Integer count);

    ArrayList<TenantParam> getByNameToVo(String name, String userName, Integer page, Integer count);

    String getNameById(Long id);

}
