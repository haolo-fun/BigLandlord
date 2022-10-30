package fun.haolo.bigLandlord.land.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.entity.OrderAdditional;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.param.OrderAdditionalParam;
import fun.haolo.bigLandlord.db.service.*;
import fun.haolo.bigLandlord.db.utils.HouseStatusConstant;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
import fun.haolo.bigLandlord.db.utils.SNUtil;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;
import fun.haolo.bigLandlord.db.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * @author haolo
 * @since 2022-10-22 14:36
 */
@Service
public class LandOrderService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderAdditionalService orderKeyService;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private SNUtil snUtil;

    /**
     * 构建租单
     *
     * @param username 房东
     * @param tenantId 租客id
     * @param houseId  房源id
     * @param count    租期
     */
    @Transactional
    public void buildOrder(String username, Long tenantId, Long houseId, Short count) {
        Long userId = userService.getUserIdByUsername(username);
        House house = houseService.getById(houseId);

        // 构建order
        Order order = new Order();
        order.setUserId(userId);
        order.setTenantId(tenantId);
        order.setHouseId(houseId);
        order.setOrderSn(snUtil.generateOrderSn());
        order.setCount(count);
        order.setOrderStatus(OrderStatusConstant.NOT_ISSUED);
        order.setPrice(house.getPrice().multiply(new BigDecimal(count))); // 价格 等于 房租*租期

        // 添加押金单
        BigDecimal deposit = house.getDeposit();
        depositService.initAdd(userId, tenantId, deposit);

        // 更新house
        house.setTenantId(tenantId);
        house.setStatus(HouseStatusConstant.HAVE_TO_RENT);
        house.setDueDate(LocalDate.now().plusMonths(count));

        orderService.save(order);
        houseService.updateById(house);
    }

    /**
     * 添加附加费用
     *
     * @param username 房东
     * @param orderSn  租单号
     * @param param
     */
    @Transactional
    public void addOrderAdditional(String username, String orderSn, OrderAdditionalParam param) {
        Long userId = userService.getUserIdByUsername(username);
        Order order = orderService.getOrderBySn(orderSn);
        if (!order.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的租单，无法完成此操作");
        OrderAdditional orderAdditional = new OrderAdditional();
        BeanUtils.copyProperties(param, orderAdditional);
        orderAdditional.setOrderId(order.getId());
        orderKeyService.save(orderAdditional);
        updatePrice(order.getId());
    }

    /**
     * 更新附加费用信息
     *
     * @param username          房东
     * @param orderAdditionalVO 附加信息
     */
    @Transactional
    public void updateOrderAdditional(String username, OrderAdditionalVO orderAdditionalVO) {
        Long userId = userService.getUserIdByUsername(username);
        OrderAdditional orderAdditional = new OrderAdditional();
        BeanUtils.copyProperties(orderAdditionalVO, orderAdditional);
        Order order = orderService.getById(orderAdditional.getOrderId());
        if (!order.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的租单，无法完成此操作");
        orderKeyService.updateById(orderAdditional);
        updatePrice(orderAdditional.getOrderId());
    }


    /**
     * 删除附加费用信息
     *
     * @param username          房东
     * @param orderAdditionalId 附加信息id
     */
    @Transactional
    public void delOrderAdditional(String username, Long orderAdditionalId) {
        Long userId = userService.getUserIdByUsername(username);
        OrderAdditional orderAdditional = orderKeyService.getById(orderAdditionalId);
        Order order = orderService.getById(orderAdditional.getOrderId());
        if (!order.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的租单，无法完成此操作");
        orderKeyService.removeById(orderAdditionalId);
        updatePrice(order.getId());
    }

    /**
     * 更改租单状态
     *
     * @param username            房东
     * @param orderSn             租单号
     * @param orderStatusConstant 租单状态（0->未下发，1->已下发，2->已支付)
     * @return 是否更改成功
     */
    public Boolean updateStatus(String username, String orderSn, Integer orderStatusConstant) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", orderSn);
        Order order = orderService.getOne(wrapper);
        if (!order.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的租单，无法完成此操作");
        Order newOrder = new Order();
        BeanUtils.copyProperties(order, newOrder, "updateTime");
        newOrder.setOrderStatus(orderStatusConstant);
        return orderService.updateById(newOrder);
    }

    /**
     * 查询所有租单
     *
     * @param username 房东
     * @param current  当前页
     * @param size     每页显示条数
     * @param desc     是否倒序
     * @return List<OrderVO>
     */
    public List<OrderVO> list(String username, long current, long size, Boolean desc) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (desc) wrapper.orderByDesc("id"); // 倒序
        return orderService.selectPage(current, size, wrapper);
    }

    /**
     * 通过租单号查询
     *
     * @param username 房东
     * @param sn       租单号
     * @return List<OrderVO>
     */
    public List<OrderVO> getByOrderSn(String username, String sn) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("order_sn", sn);
        return orderService.selectPage(1, 1, wrapper);
    }

    /**
     * 通过房源id查询
     *
     * @param username 房东
     * @param houseId  房源id
     * @param current  当前页
     * @param size     每页显示条数
     * @param desc     是否倒序
     * @return List<OrderVO>
     */
    public List<OrderVO> listByHouseId(String username, Long houseId, long current, long size, Boolean desc) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("house_id", houseId);
        if (desc) wrapper.orderByDesc("id"); // 倒序
        return orderService.selectPage(current, size, wrapper);
    }

    /**
     * 根据租客id查询
     *
     * @param username 房东
     * @param tenantId 租客id
     * @param current  当前页
     * @param size     每页显示条数
     * @return List<OrderVO>
     */
    public List<OrderVO> listByTenantId(String username, Long tenantId, long current, long size, Boolean desc) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("tenant_id", tenantId);
        if (desc) wrapper.orderByDesc("id"); // 倒序
        return orderService.selectPage(current, size, wrapper);
    }

    /**
     * 根据租单状态查询
     *
     * @param username    房东
     * @param orderStatus 租单状态（0->未下发，1->已下发，2->已支付)
     * @param current     当前页
     * @param size        每页显示条数
     * @param desc        是否倒序
     * @return List<OrderVO>
     */
    public List<OrderVO> listByOrderStatus(String username, Integer orderStatus, long current, long size, Boolean desc) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("order_status", orderStatus);
        if (desc) wrapper.orderByDesc("id"); // 倒序
        return orderService.selectPage(current, size, wrapper);
    }


    //====================================================================================


    /**
     * 更新租单总价
     *
     * @param orderId 租单id
     */
    private void updatePrice(Long orderId) {
        BigDecimal price = new BigDecimal(0);
        Order order = orderService.getById(orderId);
        House house = houseService.getById(order.getHouseId());
        price = price.add(house.getPrice().multiply(new BigDecimal(order.getCount())));
        QueryWrapper<OrderAdditional> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        List<OrderAdditional> list = orderKeyService.list(wrapper);
        for (OrderAdditional orderAdditional : list) {
            price = price.add(orderAdditional.getValue().multiply(new BigDecimal(orderAdditional.getCount())));
        }
        Order newOrder = new Order();
        BeanUtils.copyProperties(order, newOrder, "updateTime");
        newOrder.setPrice(price);
        orderService.updateById(newOrder);
    }


}
