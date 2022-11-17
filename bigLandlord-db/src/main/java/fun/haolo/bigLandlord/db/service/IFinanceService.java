package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.Finance;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.vo.FinanceVO;

/**
 * <p>
 * 财务服务类
 * <p>
 * 1. 查询用户财务信息返回financeVO
 * 2. 根据user_id初始化财务数据
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
public interface IFinanceService extends IService<Finance> {

    /**
     * 查询用户财务信息返回financeVO
     *
     * @param username 用户名
     * @return FinanceVO
     */
    FinanceVO getMsg(String username);

    /**
     * 根据user_id初始化财务数据和初始化流水数据
     *
     * @param userId 用户id
     */
    void init(Long userId);
}
