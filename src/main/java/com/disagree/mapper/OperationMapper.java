package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/4/17.
 */
@Mapper
public interface OperationMapper {
    @Insert("insert into operation (visitUserId,visitId,appId,operType,operName,timestamp) " +
            "values (#{visitUserId},#{visitId},#{appId},#{operType},#{operName},#{timestamp})")
    void recordOperation(@Param("visitUserId") String visitUserId,
                         @Param("visitId")String visitId,
                         @Param("appId") String appId,
                         @Param("operType") String operType,
                         @Param("operName") String operName,
                         @Param("timestamp") String timestamp);

    @Select("select count(*) from operation where appId=#{appId}")
    int countOperation(@Param("appId")String appId);

    @Select("select count(*) from operation where appId=#{appId} and visitId=#{visitId}")
    int countOperation2(@Param("appId")String appId,
                        @Param("visitId")String visitId);

    @Select("select count(*) from operation where appId=#{appId} " +
            " and timestamp >=#{start} and timestamp < #{end}")
    int countOperationByTime(@Param("appId")String appId,
                        @Param("start")long start,
                        @Param("end")long end);

    @Select("select visitId from operation where appId=#{appId}")
    String[] getVisitId(@Param("appId") String appId);

    @Select("select visitId,operType,operName,timestamp from operation where appId=#{appId}")
    List<Map<String,Object>> getOperInfo(@Param("appId")String appId);

    @Delete("delete from operation where appId=#{appId}")
    void delete(@Param("appId")String appId);
}
