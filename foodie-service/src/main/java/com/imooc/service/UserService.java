package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;

public interface UserService {

    /**
     * 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBo
     * @return
     */
    Users createUser(UserBo userBo);

    /**
     * 检索用户名和密码是否匹配，用于登陆
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username,String password);
}
