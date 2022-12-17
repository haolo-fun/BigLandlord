package fun.haolo.bigLandlord.db.mapper;

import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.haolo.bigLandlord.db.vo.RoleRelationVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haolo
 * @since 2022-10-13
 */
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {

    List<RoleRelationVO> getList(long userId);
}
