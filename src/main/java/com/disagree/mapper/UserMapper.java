package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * Created by disagree on 2017/4/17.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (uid,username,phoneNumber) values (#{uid},#{username},#{phoneNumber})")
    void register(@Param("uid") String uid,
                  @Param("username") String username,
                  @Param("phoneNumber") String phoneNumber);

    @Select("select uid,username from user where username = #{username}")
    Map<String,Object> findUserName(@Param("username") String usernmae);

    @Select("select uid,phoneNumber from user where phoneNumber = #{phoneNumber}")
    Map<String,Object> findPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Select("select username,phoneNumber from user where uid = #{uid}")
    Map<String,Object> getUserInfo(@Param("uid") String uid);

    @Update("update user set nickname = #{nickname},sex = #{sex}," +
            "location = #{location},email = #{email} where uid = #{uid}")
    void update(@Param("nickname") String nickname, @Param("sex") int sex,
                @Param("location")String location, @Param("email")String email, @Param("uid")String uid);
}
