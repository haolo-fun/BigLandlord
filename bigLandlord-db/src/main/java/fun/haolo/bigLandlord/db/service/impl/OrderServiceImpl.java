package fun.haolo.bigLandlord.db.service.impl;

import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.mapper.OrderMapper;
import fun.haolo.bigLandlord.db.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
