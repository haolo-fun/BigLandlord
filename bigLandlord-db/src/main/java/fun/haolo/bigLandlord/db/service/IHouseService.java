package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.House;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.param.HouseParam;
import fun.haolo.bigLandlord.db.vo.HouseOptionsVO;
import fun.haolo.bigLandlord.db.vo.HouseVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
public interface IHouseService extends IService<House> {

    House add(HouseParam param, String username);

    boolean del(Long id, String username);

    House update(HouseParam param, String username);

    HouseVO listByUsername2VO(String username, long current, long size);

    HouseVO listByAddress2VO(String Address, String username, long current, long size);

    HouseVO listByAreaRange2VO(Integer low, Integer high, String username, long current, long size);

    HouseVO listByPriceRange2VO(Integer low, Integer high, String username, long current, long size);

    HouseVO listByStatus2VO(Integer status, String username, long current, long size);

    String getAddressById(Long id);

    List<HouseOptionsVO> getHouseOptions(String username, String address);
}
