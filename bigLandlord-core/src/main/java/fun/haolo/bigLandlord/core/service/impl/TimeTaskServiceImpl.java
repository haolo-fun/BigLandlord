package fun.haolo.bigLandlord.core.service.impl;

import fun.haolo.bigLandlord.core.service.TimedTaskService;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.service.IHouseService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
import fun.haolo.bigLandlord.db.utils.SNUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author haolo
 * @since 2023-02-27 13:53
 */
@Service
public class TimeTaskServiceImpl implements TimedTaskService {

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private SNUtil snUtil;

    @Override
    public void timeToBuildOrder() {
        List<House> needPayHouse = houseService.getNeedPayHouse();
        for (House house : needPayHouse) {
            buildOrder(house);
        }
    }

    private void buildOrder(House house) {
        Order order = new Order();
        order.setUserId(house.getUserId());
        order.setTenantId(house.getTenantId());
        order.setHouseId(house.getId());
        order.setCount((short) 0);
        order.setOrderSn(snUtil.generateOrderSn());
        order.setOrderStatus(OrderStatusConstant.NOT_ISSUED);
        order.setPrice(new BigDecimal(0));
        orderService.save(order);
    }
}
