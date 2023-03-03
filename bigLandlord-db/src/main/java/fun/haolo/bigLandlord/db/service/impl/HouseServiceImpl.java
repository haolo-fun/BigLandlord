package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.haolo.bigLandlord.db.dto.HouseDTO;
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.mapper.HouseMapper;
import fun.haolo.bigLandlord.db.param.HouseParam;
import fun.haolo.bigLandlord.db.service.IHouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.ITenantService;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.HouseStatusConstant;
import fun.haolo.bigLandlord.db.vo.HouseOptionsVO;
import fun.haolo.bigLandlord.db.vo.HouseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private IUserService userService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IHouseService houseService;

    @Override
    public House add(HouseParam param, String username) {
        House house = new House();
        BeanUtils.copyProperties(param, house, "id");
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
    public HouseVO listByUsername2VO(String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return house2VO(wrapper, current, size);
    }

    @Override
    public HouseVO listByAddress2VO(String address, String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.like("address", address);
        return house2VO(wrapper, current, size);
    }

    @Override
    public HouseVO listByAreaRange2VO(Integer low, Integer high, String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.between("area", low, high);
        return house2VO(wrapper, current, size);
    }

    @Override
    public HouseVO listByPriceRange2VO(Integer low, Integer high, String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.between("price", low, high);
        return house2VO(wrapper, current, size);
    }

    @Override
    public HouseVO listByStatus2VO(Integer status, String username, long current, long size) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status", status);
        return house2VO(wrapper, current, size);
    }

    @Override
    public String getAddressById(Long id) {
        return getById(id).getAddress();
    }

    @Override
    public List<HouseOptionsVO> getHouseOptions(String username, String address) {
        Long userId = userService.getUserIdByUsername(username);
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.like("address", address);
        List<House> houseList = getBaseMapper().selectPage(new Page<>(1, 10), wrapper).getRecords();
        List<HouseOptionsVO> list = new ArrayList<>();
        for (House house : houseList) {
            HouseOptionsVO houseOptionsVO = new HouseOptionsVO();
            houseOptionsVO.setHouseId(house.getId());
            houseOptionsVO.setAddress(house.getAddress());
            list.add(houseOptionsVO);
        }
        return list;
    }

    @Override
    public List<House> getNeedPayHouse() {
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq("status", HouseStatusConstant.HAVE_TO_RENT);
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        // 筛选数据
        return list(wrapper).stream()
                .filter(house -> {
                    // 判断是否已到期
                    boolean before = house.getDueDate().isBefore(now);
                    if (before) {
                        // 顺便更新到期house状态
                        house.setStatus(HouseStatusConstant.OVERDUE_LEASE_NOT_RETURNED);
                        houseService.updateById(house);
                    }
                    // 将未到期的数据筛选出来
                    return !before;
                })
                // 筛选需要生成月结租单的数据
                .filter(house -> house.getDueDate().getDayOfMonth() == dayOfMonth)
                .collect(Collectors.toList());
    }

    private HouseVO house2VO(QueryWrapper<House> wrapper, long current, long size) {
        Page<House> housePage = getBaseMapper().selectPage(new Page<>(current, size), wrapper);
        List<House> list = housePage.getRecords();
        List<HouseDTO> dtoList = new ArrayList<>();
        for (House house : list) {
            HouseDTO dto = new HouseDTO();
            dto.setId(house.getId());
            dto.setAddress(house.getAddress());
            dto.setArea(house.getArea());
            dto.setDeposit(house.getDeposit());
            dto.setPrice(house.getPrice());
            dto.setStatus(house.getStatus());
            if (house.getTenantId() != null) {
                dto.setTenantId(house.getTenantId());
                dto.setTenantName(tenantService.getNameById(house.getTenantId()));
            }
            dto.setDueDate(house.getDueDate());
            dtoList.add(dto);
        }
        HouseVO houseVO = new HouseVO();
        houseVO.setList(dtoList);
        houseVO.setTotal(housePage.getTotal());
        return houseVO;
    }
}
