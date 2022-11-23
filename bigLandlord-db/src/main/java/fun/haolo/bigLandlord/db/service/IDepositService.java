package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.dto.DepositDTO;
import fun.haolo.bigLandlord.db.entity.Deposit;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.vo.DepositVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface IDepositService extends IService<Deposit> {

    Deposit getBySn(String sn);

    DepositVO getBySnToVO(String username, String sn);

    /**
     * 初始化押金单
     *
     * @param userId   房东id
     * @param tenantId 租客id
     * @param price    押金金额
     * @return 是否添加成功
     */
    boolean initAdd(Long userId, Long tenantId, BigDecimal price);

    /**
     * 通过押金单编号删除
     *
     * @param username 房东名
     * @param sn       押金单编号
     */
    void delBySn(String username, String sn);

    /**
     * 更新押金状态
     *
     * @param sn                    租单号
     * @param depositStatusConstant 状态常量
     */
    void updateStatus(String username, String sn, Integer depositStatusConstant);

    /**
     * 分页查询所有押金单
     *
     * @param username 房东名
     * @param current  当前页
     * @param size     每页显示条数
     * @param desc     是否倒序
     * @return DepositVO
     */
    DepositVO AllList(String username, long current, long size, Boolean desc);

    /**
     * 根据押金单状态分页查询
     *
     * @param username              房东名
     * @param depositStatusConstant 状态常量
     * @param current               当前页
     * @param size                  每页显示条数
     * @param desc                  是否倒序
     * @return DepositVO
     */
    DepositVO listByStatus(String username, Integer depositStatusConstant, long current, long size, Boolean desc);

    /**
     * 根据押金单租客id分页查询
     *
     * @param username 房东名
     * @param tenantId 租客id
     * @param current  当前页
     * @param size     每页显示条数
     * @param desc     是否倒序
     * @return DepositVO
     */
    DepositVO listByTenantId(String username, Long tenantId, long current, long size, Boolean desc);

    List<DepositDTO> oneByTenantId(Long tenantId);
}
