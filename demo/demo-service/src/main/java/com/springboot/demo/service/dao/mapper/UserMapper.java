package com.springboot.demo.service.dao.mapper;

import com.springboot.demo.service.dao.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(String urid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String urid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserId(String userId);

    List<User> selectAll();
}