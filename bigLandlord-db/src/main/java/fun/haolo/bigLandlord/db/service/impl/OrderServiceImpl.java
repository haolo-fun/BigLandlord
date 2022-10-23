package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.mapper.OrderMapper;
import fun.haolo.bigLandlord.db.service.IHouseService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    IHouseService houseService;

    @Autowired
    ITenantService tenantService;

    @Override
    public Long getOrderIdBySn(String sn) {
        return getOrderBySn(sn).getId();
    }

    @Override
    public Order getOrderBySn(String sn) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", sn);
        return getOne(wrapper);
    }

    @Override
    public List<OrderVO> selectPage(long current, long size, Wrapper<Order> queryWrapper) {
        List<Order> orderList = getBaseMapper().selectPage(new Page<>(current, size), queryWrapper).getRecords();
        List<OrderVO> orderVOList = new ArrayList<>();
        OrderVO orderVO = new OrderVO();
        for (Order order : orderList) {
            orderVO.setOrderSn(order.getOrderSn());
            orderVO.setHouseId(order.getHouseId());
            orderVO.setAddress(houseService.getAddressById(order.getHouseId()));
            orderVO.setTenantId(order.getTenantId());
            orderVO.setName(tenantService.getNameById(order.getTenantId()));
            orderVO.setCount(order.getCount());
            orderVO.setOrderStatus(order.getOrderStatus());
            orderVO.setPrice(order.getPrice());
            orderVO.setPayId(order.getPayId());
            orderVO.setPayTime(order.getPayTime());
            orderVO.setCreateTime(order.getCreateTime());

            orderVOList.add(orderVO);
        }
        return orderVOList;
    }
}
