package fun.haolo.bigLandlord.db.mapper;

import fun.haolo.bigLandlord.db.dto.SurrenderDTO;
import fun.haolo.bigLandlord.db.entity.House;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface HouseMapper extends BaseMapper<House> {

    Long getNeedRefundDepositId(Long tenantId);

    SurrenderDTO getNeedRefundDepositAndOrderId(Long tenantId);

}
