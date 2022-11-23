package fun.haolo.bigLandlord.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.dto.OrderDTO;
import fun.haolo.bigLandlord.db.vo.OrderVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface IOrderService extends IService<Order> {

    Long getOrderIdBySn(String sn);

    Order getOrderBySn(String sn);

    OrderVO selectPage(long current, long size, QueryWrapper<Order> queryWrapper);

    List<OrderDTO> oneByTenantId(long tenantId);
}
