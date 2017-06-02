package com.disagree.controller;

import com.disagree.mapper.OperationMapper;
import com.disagree.mapper.VisitMapper;
import com.disagree.utils.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by disagree on 2017/5/1.
 */
@RestController
public class VisitController {

    @Autowired
    VisitMapper visitMapper;

    @Autowired
    OperationMapper operationMapper;

    Jedis redis = new Jedis("localhost", 6379);

    //查询某应用的浏览实时数据
    @RequestMapping(value = "/visitCountRealTime/get", produces = "application/json")
    public Integer getVisitCountRealTime(@RequestParam(value = "appId") String appId) {
//        long end = Utils.getMinuteBeginTimestamp();
//        long start = end - 60 * 1000;
//        System.out.println("查询的是从" + start + "到" + end + "的时间戳");
//        return visitMapper.countVisitByTime(appId, start, end);
        long timestamp = Utils.getMinuteBeginTimestamp();
        if (redis.hget("appId:" + appId + ":" +String.valueOf(timestamp), "visit") == null)
            return 0;
        redis.close();
        return Integer.valueOf(redis.hget("appId:" + appId + ":" + String.valueOf(timestamp), "visit"));
    }

    //查询20分钟前到现在的浏览趋势
    @RequestMapping(value = "/visitTrend/get", produces = "application/json")
    public int[] getVisitTrend(@RequestParam(value = "appId") String appId) {
        int[] result = new int[20];
        long timestamp = Utils.getMinuteBeginTimestamp() - 1000L * 60L * 20L;
        for (int i = 0; i < 20; i++) {
            if (redis.hget("appId:" + appId + ":" +String.valueOf(timestamp + i * 60L * 1000L), "visit") == null)
                result[i] = 0;
            else
                result[i] = Integer.valueOf(redis.hget("appId:" + appId + ":" + String.valueOf(timestamp + i * 60L * 1000L), "visit"));
        }
        return result;
    }

    //查询用户停留时间图
    @RequestMapping(value = "/stopTime/get", produces = "application/json")
    public int[] getStopTime(@RequestParam(value = "appId") String appId) {
        int[] result = new int[8];
        Integer[] stopTime = visitMapper.getStopTime(appId);
        for (int time : stopTime) {
            if (time < 1) {
                result[0]++;
            } else if (time >= 1 && time < 10) {
                result[1]++;
            } else if (time >= 10 && time < 20) {
                result[2]++;
            } else if (time >= 20 && time < 30) {
                result[3]++;
            } else if (time >= 30 && time < 40) {
                result[4]++;
            } else if (time >= 40 && time < 50) {
                result[5]++;
            } else if (time <= 50 && time < 60) {
                result[6]++;
            } else if (time >= 60) {
                result[7]++;
            }
        }
        return result;
    }

    //查询浏览列表
    @RequestMapping(value = "visitList/get", produces = "application/json")
    public List<Map<String, Object>> getVisitList(@RequestParam(value = "appId") String appId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] ips = visitMapper.getUserIp(appId);
        for (String ip : ips) {
            if (ip != null && !ip.equals("")) {
                Map<String, Object> map = new HashedMap();
                map.put("userIp", ip);
                List<Map<String, Object>> info = visitMapper.getInfo(appId, ip);
                map.put("browser", info.get(0).get("browser").toString());
                map.put("os", info.get(0).get("os").toString());
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(visitMapper.getMinTime(appId, ip));
                map.put("firstVisit", sdf.format(date));
                date = new Date(visitMapper.getMaxTime(appId, ip));
                map.put("lastVisit", sdf.format(date));
                map.put("sessionCount", info.size());
                list.add(map);
            }
        }
        return list;
    }

    //查询用户轨迹
    @RequestMapping(value = "userTrack/get", produces = "application/json")
    public List<Map<String, Object>> getUserTrack(@RequestParam(value = "appId") String appId,
                                                  @RequestParam(value = "userIp") String userIp) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> info = visitMapper.getInfo(appId, userIp);
        for (int i = 0; i < info.size(); i++) {
            Map<String, Object> map = new HashedMap();
            map.put("title", info.get(i).get("title").toString());
            map.put("stopTime", info.get(i).get("visitTime").toString());
            map.put("timestamp", info.get(i).get("timestamp").toString());
            int operCount = operationMapper.countOperation2(appId, info.get(i).get("visitId").toString());
            map.put("operCount", operCount);
            list.add(map);
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                String map1value = o1.get("timestamp").toString();
                String map2value = o2.get("timestamp").toString();

                if (map1value != null) {
                    return map2value.compareTo(map1value);
                }
                return 0;
            }
        });
        return list;
    }

}
