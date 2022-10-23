package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.mapper.HouseMapper;
import fun.haolo.bigLandlord.db.param.HouseParam;
import fun.haolo.bigLandlord.db.service.IHouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements IHouseService {

    @Autowired
    IUserService userService;

    @Override
    public House add(HouseParam param, String username) {
        House house = new House();
        BeanUtils.copyProperties(param, house);
        Long userId = userService.getUserIdByUsername(username);
        house.setUserId(userId);
        boolean save = save(house);
        return save ? house : null;
    }

    @Override
    public boolean del(Long id, String username) {
        Long userId = userService.getUserIdByUsername(username);
        House house = getById(id);
        if (!house.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的房子，无法完成此操作");
        return removeById(id);
    }

    @Override
    public House update(HouseParam param, String username) {
        Long userId = userService.getUserIdByUsername(username);
        House house = getById(param.getId());
        if (!house.getUserId().equals(userId)) throw new UnauthorizedException("这不是你的房子，无法完成此操作");
        BeanUtils.copyProperties(param, house);
        boolean b = updateById(house);
        return b ? house : null;
    }

    @Override
    public List<HouseParam> listByUsername2VO(String username) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return house2VO(list(wrapper));
    }

    @Override
    public List<HouseParam> listByAreaRange2VO(Integer low, Integer high, String username) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.between("area", low, high);
        return house2VO(list(wrapper));
    }

    @Override
    public List<HouseParam> listByPriceRange2VO(Integer low, Integer high, String username) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.between("price", low, high);
        return house2VO(list(wrapper));
    }

    @Override
    public List<HouseParam> listByStatus2VO(Integer status, String username) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status", status);
        return house2VO(list(wrapper));
    }

    @Override
    public String getAddressById(Long id) {
        return getById(id).getAddress();
    }

    private List<HouseParam> house2VO(List<House> list) {
        List<HouseParam> voList = new ArrayList<>();
        for (House house : list) {
            HouseParam vo = new HouseParam();
            BeanUtils.copyProperties(house, vo);
            voList.add(vo);
        }
        return voList;
    }
}
