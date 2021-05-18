package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    public static final String USER_FACE = "http://pic.616pic.com/ys_b_img/00/44/76/IUJ3YQSjx1.jpg";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        Users users = usersMapper.selectOneByExample(userExample);
        return users == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBo userBo) {
        Users users = new Users();
        users.setId(sid.nextShort());
        users.setUsername(userBo.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认用户昵称同用户名
        users.setNickname(userBo.getUsername());
        //默认头像
        users.setFace(USER_FACE);
        //设置生日
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //默认性别
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        try {
            criteria.andEqualTo("password", MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }
}
