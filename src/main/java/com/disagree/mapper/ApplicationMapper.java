package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/4/25.
 */
@Mapper
public interface ApplicationMapper {
    @Insert("insert into application (appId,uid,appName,url,apdexT) values (#{appId},#{uid},#{appName},#{url},#{apdexT})")
    void createApp(@Param("appId") String appId,
                   @Param("uid") String uid,
                   @Param("appName") String appName,
                   @Param("url") String url,
                   @Param("apdexT") int apdexT);

    @Select("select appId,appName,url from application where uid=#{uid}")
    List<Map<String,Object>> getAppInfo(@Param("uid")String uid);

    @Select("select url from application where appId=#{appId}")
    String getAppUrl(@Param("appId")String appId);

    @Delete("delete from application where appId=#{appId}")
    void deleteApp(@Param("appId")String appId);

    @Update("update application set appName=#{appName},url=#{url} where appId=#{appId}")
    void updateApp(@Param("appId")String appId,
                   @Param("appName")String appName,
                   @Param("url")String url);

    @Update("update application set apdexT = #{apdexT},dataSaveTime=#{dataSaveTime}" +
            "where appId=#{appId}")
    void updateAppConfig(@Param("appId")String appId,
                         @Param("apdexT")String apdexT,
                         @Param("dataSaveTime")String dataSaveTime);

    @Select("select apdexT from application where appId=#{appId}")
    Integer getApdexT(@Param("appId")String appId);

    @Select("select apdexT,dataSaveTime from application where appId=#{appId}")
    Map<String,Object> getAppConfig(@Param("appId")String appId);
}
