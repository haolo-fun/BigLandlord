package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.OrderAdditional;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface IOrderAdditionalService extends IService<OrderAdditional> {

    List<OrderAdditionalVO> getListByOrderId(Long orderId);
}
