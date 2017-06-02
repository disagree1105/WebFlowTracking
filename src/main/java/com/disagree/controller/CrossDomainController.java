package com.disagree.controller;

import com.alibaba.fastjson.JSON;
import com.disagree.bean.cassandraBean.Operation;
import com.disagree.mapper.*;
import com.disagree.bean.cassandraBean.Error;
import com.disagree.utils.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;


/**
 * Created by disagree on 2017/4/17.
 */
@RestController
public class CrossDomainController {
    @Autowired
    VisitMapper visitMapper;

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    ErrorMapper errorMapper;

    @Autowired
    HeatmapMapper heatmapMapper;

    @Autowired
    ErrorRepository errorRepository;

    @Autowired
    OperationRepository operationRepository;

    Jedis redis = new Jedis("localhost",6379);

    @RequestMapping(value = "/test", produces = "application/json")
    public String test(@RequestParam(value = "callback") String callback,
                       @RequestParam(value = "username") String username) {
        String json = "{test:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value = "/recordVisit", produces = "text/html")
    public String recordVisit(@RequestParam(value = "callback") String callback,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "userIp") String userIp,
                              @RequestParam(value = "visitUserId") String visitUserId,
                              @RequestParam(value = "visitId") String visitId,
                              @RequestParam(value = "appId") String appId,
                              @RequestParam(value = "city") String city,
                              @RequestParam(value = "cityCode") String cityCode,
                              @RequestParam(value = "browser") String browser,
                              @RequestParam(value = "os") String os,
                              @RequestParam(value = "rspTime") String rspTime,
                              @RequestParam(value = "timestamp") String timestamp) {
        //先查询该用户的运营商
        String path = "http://ip.taobao.com/service/getIpInfo.php?ip=" + userIp;
        String info = "";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            String inputline = "";
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader buffer = new BufferedReader(inStream);

            while ((inputline = buffer.readLine()) != null) {
                info += inputline;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonob = JSONObject.fromObject((JSONObject.fromObject(info).getString("data")));
        String isp = StringEscapeUtils.escapeSql(jsonob.getString("isp"));
        System.out.println("isp===" + isp);
        visitMapper.recordVisit(appId, title, visitUserId, visitId, userIp, city, cityCode, browser, os, isp, rspTime, timestamp, 0);

        String redisTimestamp= String.valueOf(Utils.getMinuteBeginTimestamp());
        String redisKey="appId:"+appId+":"+redisTimestamp;
        redis.hincrBy(redisKey,"visit",1);//给该分钟的key的值+1
        redis.expire(redisKey, 60*30);//30分钟后过期
        String json = "{recordVisit:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value = "/recordOperation", produces = "text/html")
    public String recordOperation(@RequestParam(value = "callback") String callback,
                                  @RequestParam(value = "visitUserId") String visitUserId,
                                  @RequestParam(value = "visitId") String visitId,
                                  @RequestParam(value = "appId") String appId,
                                  @RequestParam(value = "clickInfo") String clickInfo) {
        JSONArray array = JSONArray.fromObject(clickInfo);
        if (array.size() > 0) {
            for (Object anArray : array) {
                Map map = JSON.parseObject(anArray.toString());
                operationMapper.recordOperation(visitUserId, visitId, appId,
                        map.get("operType").toString(),
                        map.get("operName").toString(),
                        map.get("timestamp").toString());
                String operId= UUID.randomUUID().toString();
                Operation operation=new Operation(operId,visitUserId,visitId,appId,map.get("operType").toString(),
                        map.get("operName").toString(),
                        map.get("timestamp").toString());
                operationRepository.save(operation);
            }
        }
        String redisTimestamp= String.valueOf(Utils.getMinuteBeginTimestamp());
        String redisKey="appId:"+appId+":"+redisTimestamp;
        redis.hincrBy(redisKey,"operation",array.size());//给该分钟的key的值+1
        redis.expire(redisKey, 60*30);//30分钟后过期
        String json = "{recordOperation:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value = "/stopTime/update", produces = "text/html")
    public String updateStopTime(@RequestParam(value = "callback") String callback,
                                 @RequestParam(value = "appId") String appId,
                                 @RequestParam(value = "visitId") String visitId,
                                 @RequestParam(value = "stopTime") int time) {
        visitMapper.updateVisitTime(appId, time, visitId);
        String json = "{updateStopTime:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value = "/recordHeatmap", produces = "text/html")
    public String recordHeatmap(@RequestParam(value = "callback") String callback,
                                @RequestParam(value = "appId") String appId,
                                @RequestParam(value = "heatmapList") String heatmapList) {
        JSONArray array = JSONArray.fromObject(heatmapList);
        if (array.size() > 0) {
            for (Object anArray : array) {
                Map map = JSON.parseObject(anArray.toString());
                String x = map.get("x").toString();
                String y = map.get("y").toString();
                if (heatmapMapper.check(appId, x, y) == null) {
                    heatmapMapper.insert(appId, x, y, 1);
                } else {
                    heatmapMapper.update(appId, x, y);
                }
            }
        }
        String json = "{recordOperation:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value = "/recordError", produces = "text/html")
    public String recordError(@RequestParam(value = "callback") String callback,
                              @RequestParam("errorId") String errorId,
                              @RequestParam("visitId") String visitId,
                              @RequestParam("appId") String appId,
                              @RequestParam("errorMessage") String errorMessage,
                              @RequestParam("scriptURI") String scriptURI,
                              @RequestParam("lineNumber") String lineNumber,
                              @RequestParam("columnNumber") String columnNumber,
                              @RequestParam("timestamp") String timestamp) {
        Error error = new Error(errorId, visitId,appId,errorMessage,scriptURI,lineNumber,columnNumber,timestamp);
        errorRepository.save(error);
        errorMapper.recordError(errorId,visitId, appId, errorMessage, scriptURI, lineNumber, columnNumber, timestamp);
        String redisTimestamp= String.valueOf(Utils.getMinuteBeginTimestamp());
        String redisKey="appId:"+appId+":"+redisTimestamp;
        redis.hincrBy(redisKey,"error",1);//给该分钟的key的值+1
        redis.expire(redisKey, 60*30);//30分钟后过期
        String json = "{recordError:'success'}";
        return callback + "(" + json + ")";
    }

    @RequestMapping(value="/test/redis",produces = "text/html")
    public String test(){

        return "success";
    }

}