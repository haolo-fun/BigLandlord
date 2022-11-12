package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.OrderAdditional;
import fun.haolo.bigLandlord.db.mapper.OrderAdditionalMapper;
import fun.haolo.bigLandlord.db.service.IOrderAdditionalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;
import org.springframework.beans.BeanUtils;
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
public class OrderAdditionalServiceImpl extends ServiceImpl<OrderAdditionalMapper, OrderAdditional> implements IOrderAdditionalService {

    @Override
    public List<OrderAdditionalVO> getListByOrderId(Long orderId) {
        QueryWrapper<OrderAdditional> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        List<OrderAdditional> list = list(wrapper);
        List<OrderAdditionalVO> voList = new ArrayList<>();
        for (OrderAdditional orderAdditional : list) {
            OrderAdditionalVO orderAdditionalVO = new OrderAdditionalVO();
            BeanUtils.copyProperties(orderAdditional,orderAdditionalVO);
            voList.add(orderAdditionalVO);
        }
        return voList;
    }
}
