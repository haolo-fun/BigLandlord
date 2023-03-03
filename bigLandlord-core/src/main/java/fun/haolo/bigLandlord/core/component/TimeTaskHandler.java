package fun.haolo.bigLandlord.core.component;

import fun.haolo.bigLandlord.core.service.TimedTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author haolo
 * @since 2023-02-27 17:08
 */
@Component
public class TimeTaskHandler {

    @Autowired
    private TimedTaskService timedTaskService;

    /**
     * 定时生成需要结算费用的房屋订单
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task1(){
        timedTaskService.timeToBuildOrder();
    }

//    @Scheduled(cron = "*/5 * * * * ?")
//    public void test(){
//        System.out.println("test");
//    }
}
