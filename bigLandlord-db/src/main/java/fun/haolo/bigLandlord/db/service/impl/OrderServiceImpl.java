package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.mapper.OrderMapper;
import fun.haolo.bigLandlord.db.service.IHouseService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.dto.OrderDTO;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
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
    public OrderVO selectPage(long current, long size, QueryWrapper<Order> queryWrapper) {
        Page<Order> orderPage = getBaseMapper().selectPage(new Page<>(current, size), queryWrapper);
        List<Order> orderList = orderPage.getRecords();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        OrderDTO orderDTO = new OrderDTO();
        for (Order order : orderList) {
            orderDTO.setOrderSn(order.getOrderSn());
            orderDTO.setHouseId(order.getHouseId());
            orderDTO.setAddress(houseService.getAddressById(order.getHouseId()));
            orderDTO.setTenantId(order.getTenantId());
            orderDTO.setName(tenantService.getNameById(order.getTenantId()));
            orderDTO.setCount(order.getCount());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setPrice(order.getPrice());
            orderDTO.setPayId(order.getPayId());
            orderDTO.setPayTime(order.getPayTime());
            orderDTO.setCreateTime(order.getCreateTime());

            orderDTOList.add(orderDTO);
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setList(orderDTOList);
        orderVO.setTotal(orderPage.getTotal());
        return orderVO;
    }

    @Override
    public List<OrderDTO> oneByTenantId(long tenantId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("tenant_id",tenantId);
//        wrapper.eq("order_status", OrderStatusConstant.HAS_BEEN_ISSUED);
        wrapper.orderByDesc("id");
        OrderVO orderVO = selectPage(1, 1, wrapper);
        return orderVO.getList();
    }
}
