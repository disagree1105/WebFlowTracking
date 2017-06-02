package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/4/17.
 */
@Mapper
public interface VisitMapper {
    @Insert("insert into visit (appId,title,visitUserId,visitId,userIp,city,cityCode,browser,os,isp,rspTime,timestamp,visitTime)" +
            "values (#{appId},#{title},#{visitUserId},#{visitId},#{userIp},#{city},#{cityCode},#{browser},#{os},#{isp},#{rspTime},#{timestamp},#{visitTime})")
    void recordVisit(@Param("appId") String appId,
                     @Param("title")String title,
                     @Param("visitUserId") String visitUserId,
                     @Param("visitId")String visitId,
                     @Param("userIp") String userIp,
                     @Param("city") String city,
                     @Param("cityCode") String cityCode,
                     @Param("browser") String browser,
                     @Param("os") String os,
                     @Param("isp")String isp,
                     @Param("rspTime") String rspTime,
                     @Param("timestamp") String timestamp,
                     @Param("visitTime") int visitTime);

    @Select("select count(*) from visit where appId=#{appId}")
    int countVisit(@Param("appId") String appId);

    @Select("select count(*) from visit where appId=#{appId} " +
            " and timestamp >=#{start} and timestamp < #{end}")
    int countVisitByTime(@Param("appId") String appId,
                         @Param("start") long start,
                         @Param("end") long end);

    @Select("select avg(rspTime) from visit where appId=#{appId} " +
            " and timestamp >=#{start} and timestamp <= #{end}")
    Integer avgRspTimeToday(@Param("appId") String appId, @Param("start") long start, @Param("end") long end);

    @Select("select avg(rspTime) from visit where appId=#{appId}")
    Integer avgRspTime(@Param("appId") String appId);

    @Select("select cityCode from visit where appId=#{appId}")
    String[] getCityCode(@Param("appId") String appId);

    @Select("select count(*) from visit where appId=#{appId} and rspTime <= #{apdexT} ")
    int countSatisfy(@Param("appId") String appId,
                     @Param("apdexT") int apdexT);

    @Select("select count(*) from visit where appId=#{appId} and rspTime>#{apdexT} and rspTime < #{apdexTfour} ")
    int countTolerate(@Param("appId") String appId,
                      @Param("apdexT") int apdexT,
                      @Param("apdexTfour") int apdexTfour);

    @Select("select count(*) from visit where appId=#{appId} and rspTime >= #{apdexTfour} ")
    int countDisappoint(@Param("appId") String appId,
                        @Param("apdexTfour") int apdexTfour);

    @Select("select browser from visit where appId=#{appId}")
    String[] getBrowser(@Param("appId") String appId);

    @Select("select isp from visit where appId=#{appId}")
    String[] getIsp(@Param("appId") String appId);

    @Select("select os from visit where appId=#{appId}")
    String[] getOs(@Param("appId") String appId);

    @Select("select browser from visit where appId=#{appId} and visitId=#{visitId}")
    String[] getBrowser2(@Param("appId") String appId,
                        @Param("visitId")String visitId);

    @Select("select isp from visit where appId=#{appId} and visitId=#{visitId}")
    String[] getIsp2(@Param("appId") String appId,
                     @Param("visitId")String visitId);

    @Select("select os from visit where appId=#{appId} and visitId=#{visitId}")
    String[] getOs2(@Param("appId") String appId,
                     @Param("visitId")String visitId);

    @Select("select visitTime from visit where appId=#{appId}")
    Integer[] getStopTime(@Param("appId")String appId);

    @Select("select city,browser,os from visit where appId=#{appId} and visitId=#{visitId}")
    List<Map<String,Object>> getOperList(@Param("appId")String appId,
                                   @Param("visitId")String visitId);

    @Select("select DISTINCT userIp from visit where appId=#{appId}")
    String[] getUserIp(@Param("appId")String appId);

    @Select("select * from visit where appId=#{appId} and userIp=#{userIp}")
    List<Map<String,Object>> getInfo(@Param("appId")String appId,@Param("userIp")String userIp);

    @Select("select max(timestamp) from visit where appId=#{appId} and userIp=#{userIp}")
    long getMaxTime(@Param("appId")String appId,@Param("userIp")String userIp);

    @Select("select min(timestamp) from visit where appId=#{appId} and userIp=#{userIp}")
    long getMinTime(@Param("appId")String appId,@Param("userIp")String userIp);

    @Update("update visit set visitTime=#{visitTime} where appId=#{appId} and visitId=#{visitId}")
    void updateVisitTime(@Param("appId")String appId,
                         @Param("visitTime")int visitTime,
                         @Param("visitId")String visitId);

    @Delete("delete from visit where appId=#{appId}")
    void delete(@Param("appId")String appId);
}
