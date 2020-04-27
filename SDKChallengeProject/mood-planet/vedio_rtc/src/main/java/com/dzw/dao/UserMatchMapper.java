package com.dzw.dao;

import com.dzw.entity.UserMatch;
import com.dzw.entity.UserMatchExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMatchMapper {
    long countByExample(UserMatchExample example);

    int deleteByExample(UserMatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserMatch record);

    int insertSelective(UserMatch record);

    List<UserMatch> selectByExample(UserMatchExample example);

    UserMatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMatch record, @Param("example") UserMatchExample example);

    int updateByExample(@Param("record") UserMatch record, @Param("example") UserMatchExample example);

    int updateByPrimaryKeySelective(UserMatch record);

    int updateByPrimaryKey(UserMatch record);
}