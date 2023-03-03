package fun.haolo.bigLandlord.core.service;

/**
 * @author haolo
 * @since 2023-02-27 13:48
 */
public interface TimedTaskService {

    /**
     * 构建月结订单（用来收水电费什么的）
     */
    void timeToBuildOrder();
}
