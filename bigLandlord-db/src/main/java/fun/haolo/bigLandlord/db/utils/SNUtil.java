package fun.haolo.bigLandlord.db.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.Deposit;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.service.IDepositService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author haolo
 * @since 2022-10-30 14:21
 */
@Component
public class SNUtil {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDepositService depositService;

    /**
     * 构建租单号
     *
     * @return 租单号
     */
    public String generateOrderSn() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByOrderSn(orderSn) != 0) {
            orderSn = now + getRandomNum(6);
        }
        return orderSn;
    }

    public String generateDepositSn() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByDepositSn(orderSn) != 0) {
            orderSn = now + getRandomNum(6);
        }
        return orderSn;
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private int countByOrderSn(String orderSn) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", orderSn);
        long count = orderService.count(wrapper);
        return (int) count;
    }

    private int countByDepositSn(String depositSn) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("deposit_sn", depositSn);
        long count = depositService.count(wrapper);
        return (int) count;
    }
}
