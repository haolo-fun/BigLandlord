package fun.haolo.bigLandlord.core.service;

import fun.haolo.bigLandlord.db.entity.User;

/**
 * @Author haolo
 * @Date 2022-10-13 20:37
 * @Description
 */
public interface LoginService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username,String password);

    /**
     * 注册
     * @param user
     * @return user信息对象
     */
    User register(User user);
}
