package com.disagree.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/4/17.
 */
@Mapper
public interface ErrorMapper {
    @Insert("insert into error (errorId,visitId,appId,errorMessage,scriptURI,lineNumber,columnNumber,timestamp)" +
            "values (#{errorId},#{visitId},#{appId},#{errorMessage},#{scriptURI},#{lineNumber}," +
            "#{columnNumber},#{timestamp})")
    void recordError(@Param("errorId") String errorId,
                     @Param("visitId") String visitId,
                     @Param("appId") String appId,
                     @Param("errorMessage") String errorMessage,
                     @Param("scriptURI") String scriptURI,
                     @Param("lineNumber") String lineNumber,
                     @Param("columnNumber") String columnNumber,
                     @Param("timestamp") String timestamp);

    @Select("select count(*) from error where appId=#{appId}")
    int countError(@Param("appId") String appId);

    @Select("select count(*) from error where appId=#{appId} " +
            " and timestamp >=#{start} and timestamp < #{end}")
    int countErrorByTime(@Param("appId") String appId,
                         @Param("start") long start,
                         @Param("end") long end);

    @Select("select count(*) from error where appId=#{appId} " +
            " and timestamp >=#{start} and timestamp <= #{end}")
    int countErrorToday(@Param("appId") String appId,
                        @Param("start") long start,
                        @Param("end") long end);

    @Select("select visitId from error where appId=#{appId}")
    String[] getVisitId(@Param("appId") String appId);

    @Select("select DISTINCT errorMessage from error where appId=#{appId}")
    String[] getErrorMessage(@Param("appId") String appId);

    @Select("select errorId from error where errorMessage=#{errorMessage}")
    String[] getErrorId(@Param("errorMessage") String errorMessage);

    @Select("select count(*) from error where appId=#{appId} and" +
            " errorMessage=#{errorMessage}")
    int countSameError(@Param("appId") String appId,
                       @Param("errorMessage") String errorMessage);

    @Select("select max(timestamp) from error where appId=#{appId} and " +
            "errorMessage=#{errorMessage}")
    long getMaxTime(@Param("appId") String appId, @Param("errorMessage") String errorMessage);

    @Select("select min(timestamp) from error where appId=#{appId} and " +
            "errorMessage=#{errorMessage}")
    long getMinTime(@Param("appId") String appId, @Param("errorMessage") String errorMessage);

    @Select("select visitId,errorMessage,scriptURI,lineNumber,columnNumber,timestamp " +
            "from error where appId=#{appId} and errorMessage=#{errorMessage}")
    List<Map<String, Object>> getErrorInfo(@Param("appId") String appId,
                                           @Param("errorMessage") String errorMessage);

    @Delete("delete from error where appId=#{appId}")
    void delete(@Param("appId") String appId);
}

