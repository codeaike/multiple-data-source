package com.springboot.demo.web.service;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.springboot.demo.service.dao.domain.User;
import com.springboot.demo.service.dao.mapper.UserMapper;
import com.springboot.demo.service.datasource.annotation.TargetDataSource;
import com.springboot.demo.service.datasource.type.EnumDataSourceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addUser(User user) {
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new RuntimeException("exception");
        }
        return result > 0;
    }

    @TargetDataSource(EnumDataSourceType.MYSQL_BACKUP)
    public User queryUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @TargetDataSource(EnumDataSourceType.MYSQL)
    public List<User> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAll();
    }
}
