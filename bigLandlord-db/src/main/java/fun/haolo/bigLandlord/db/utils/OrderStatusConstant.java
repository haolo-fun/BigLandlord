package fun.haolo.bigLandlord.db.utils;

/**
 * @author haolo
 * @since 2022-10-30 16:20
 */
public class OrderStatusConstant {
    // 未发布
    public static final Integer NOT_ISSUED = 0;
    // 已发布
    public static final Integer HAS_BEEN_ISSUED = 1;
    // 已支付
    public static final Integer HAVE_TO_PAY = 2;
    // 过期
    public static final Integer PAST_DUE = 3;
}
