package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.RunningTally;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.dto.RunningTallyDTO;
import fun.haolo.bigLandlord.db.vo.RunningTallyVO;

import java.util.List;

/**
 * <p>
 * 资金流水服务类
 * <p>
 * 1. 查询用户所有流水
 * 2. 查询最近10条流水
 * 3. 查询用户所有押金流水
 * 4. 查询用户所有租金流水
 * 5. 生成押金流水并更新finance
 * 6. 生成租金流水并更新finance
 *
 * </p>
 *
 * @author haolo
 * @since 2022-11-15
 */
public interface IRunningTallyService extends IService<RunningTally> {

    /**
     * 查询用户所有流水
     *
     * @param username 用户名
     * @param current  当前页
     * @param size     每页显示条数
     * @return List<RunningTallyDTO>
     */
    RunningTallyVO getAll(String username, long current, long size);

    /**
     * 查询最近10条流水
     *
     * @param username 用户名
     * @return List<RunningTallyDTO>
     */
    List<RunningTallyDTO> getTen(String username);

    /**
     * 查询用户所有押金流水
     *
     * @param username 用户名
     * @param current  当前页
     * @param size     每页显示条数
     * @return List<RunningTallyDTO>
     */
    RunningTallyVO getByDeposit(String username, long current, long size);

    /**
     * 查询用户所有租金流水
     *
     * @param username 用户名
     * @param current  当前页
     * @param size     每页显示条数
     * @return List<RunningTallyDTO>
     */
    RunningTallyVO getByRent(String username, long current, long size);

    /**
     * 生成押金流水并更新finance
     *
     * @param userId 用户id
     * @param sn     单号
     * @param price  流水金额（区分正负数）
     */
    void saveDeposit(long userId, String sn, String price);

    /**
     * 生成租金流水并更新finance
     *
     * @param userId 用户id
     * @param sn     单号
     * @param price  流水金额（区分正负数）
     */
    void saveRent(long userId, String sn, String price);

}
