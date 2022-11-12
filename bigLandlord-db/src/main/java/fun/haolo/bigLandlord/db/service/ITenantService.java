package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.dto.TenantDTO;
import fun.haolo.bigLandlord.db.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.param.TenantParam;
import fun.haolo.bigLandlord.db.vo.TenantOptionsVO;
import fun.haolo.bigLandlord.db.vo.TenantVO;

import java.util.ArrayList;
import java.util.List;

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

    TenantVO getListToVo(String userName, boolean desc, long current, long size);

    TenantVO getByNameToVo(String name, String userName, long current, long size);

    List<TenantOptionsVO> getByNameToOptionsVO(String name, String userName);

    String getNameById(Long id);

    TenantDTO getById(String username, Long id);

}
