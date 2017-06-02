package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/5/6.
 */
@Mapper
public interface HeatmapMapper {
    @Insert("insert into heatmap (appId,x,y,num) values (#{appId},#{x},#{y},#{num})")
    void insert(@Param("appId") String appId,
                @Param("x")String x,
                @Param("y")String y,
                @Param("num")int sum);

    @Select("select * from heatmap where appId=#{appId} and x=#{x} and y=#{y}")
    Map<String,Object> check(@Param("appId") String appId,
                             @Param("x")String x,
                             @Param("y")String y);

    @Select("select x,y,num from heatmap where appId=#{appId}")
    List<Map<String,Object>> getHeatMap(@Param("appId")String appId);

    @Update("update heatmap set num=num+1 where appId=#{appId} and x=#{x} " +
            "and y=#{y}")
    void update(@Param("appId") String appId,
                @Param("x")String x,
                @Param("y")String y);

    @Delete("delete from heatmap where appId=#{appId}")
    void delete(@Param("appId")String appId);
}
