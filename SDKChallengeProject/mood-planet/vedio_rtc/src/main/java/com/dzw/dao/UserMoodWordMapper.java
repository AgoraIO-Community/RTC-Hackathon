package com.dzw.dao;

import com.dzw.entity.UserMoodWord;
import com.dzw.entity.UserMoodWordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMoodWordMapper {
    long countByExample(UserMoodWordExample example);

    int deleteByExample(UserMoodWordExample example);

    int deleteByPrimaryKey(@Param("id") Integer id, @Param("userId") Integer userId, @Param("userMoodId") Integer userMoodId);

    int insert(UserMoodWord record);

    int insertSelective(UserMoodWord record);

    List<UserMoodWord> selectByExample(UserMoodWordExample example);

    UserMoodWord selectByPrimaryKey(@Param("id") Integer id, @Param("userId") Integer userId, @Param("userMoodId") Integer userMoodId);

    int updateByExampleSelective(@Param("record") UserMoodWord record, @Param("example") UserMoodWordExample example);

    int updateByExample(@Param("record") UserMoodWord record, @Param("example") UserMoodWordExample example);

    int updateByPrimaryKeySelective(UserMoodWord record);

    int updateByPrimaryKey(UserMoodWord record);
}