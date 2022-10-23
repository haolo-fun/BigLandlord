package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.House;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.param.HouseParam;

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

    List<HouseParam> listByUsername2VO(String username);

    List<HouseParam> listByAreaRange2VO(Integer low, Integer high, String username);

    List<HouseParam> listByPriceRange2VO(Integer low, Integer high, String username);

    List<HouseParam> listByStatus2VO(Integer status, String username);

    String getAddressById(Long id);
}
