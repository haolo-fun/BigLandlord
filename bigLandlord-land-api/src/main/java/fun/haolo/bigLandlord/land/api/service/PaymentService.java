package fun.haolo.bigLandlord.land.api.service;

import fun.haolo.bigLandlord.core.service.NotifyService;
import fun.haolo.bigLandlord.db.dto.DepositDTO;
import fun.haolo.bigLandlord.db.dto.OrderDTO;
import fun.haolo.bigLandlord.db.service.IDepositService;
import fun.haolo.bigLandlord.db.service.IOrderAdditionalService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import fun.haolo.bigLandlord.db.vo.OrderAdditionalVO;
import fun.haolo.bigLandlord.db.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-22 14:53
 */
@Service
public class PaymentService {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderAdditionalService orderAdditionalService;

    public PaymentVO getPaymentMsg(String phoneNumber, String code) {
        // 验证code有效性
        if (!notifyService.checkCode(phoneNumber, code)) {
            throw new RuntimeException("验证码错误或已失效");
        }
        // 获取租客id
        Long tenantId = tenantService.getIdByPhoneNumber(phoneNumber);

        PaymentVO paymentVO = new PaymentVO();
        List<DepositDTO> depositList = depositService.oneByTenantId(tenantId);
        List<OrderDTO> orderList = orderService.oneByTenantId(tenantId);
        if (!orderList.isEmpty()){
            List<OrderAdditionalVO> orderAdditionalList = orderAdditionalService.getListByOrderId(orderService.getOrderIdBySn(orderList.get(0).getOrderSn()));
            paymentVO.setOrderAdditionalList(orderAdditionalList);
        }
        paymentVO.setDepositList(depositList);
        paymentVO.setOrderList(orderList);

        return paymentVO;
    }
}
