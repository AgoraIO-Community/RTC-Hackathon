package com.dzw.dao;

import com.dzw.entity.UserMood;
import com.dzw.entity.UserMoodExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMoodMapper {
    long countByExample(UserMoodExample example);

    int deleteByExample(UserMoodExample example);

    int deleteByPrimaryKey(Integer id);

    @Options(useGeneratedKeys = true)
    int insert(UserMood record);

    int insertSelective(UserMood record);

    List<UserMood> selectByExample(UserMoodExample example);

    UserMood selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMood record, @Param("example") UserMoodExample example);

    int updateByExample(@Param("record") UserMood record, @Param("example") UserMoodExample example);

    int updateByPrimaryKeySelective(UserMood record);

    int updateByPrimaryKey(UserMood record);
}