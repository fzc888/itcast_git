package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findById(Integer id) {
        User user = userDao.findById(id);
        return user;
    }
}
