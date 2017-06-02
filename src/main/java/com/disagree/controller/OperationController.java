package com.disagree.controller;

import com.disagree.bean.cassandraBean.User;
import com.disagree.mapper.OperationMapper;
import com.disagree.mapper.UserRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/4/30.
 */
@RestController
public class OperationController {

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    VisitMapper visitMapper;

    @Autowired
    UserRepository userRepository;

    Jedis redis = new Jedis("localhost", 6379);

    //查询某应用的操作实时数据
    @RequestMapping(value = "/operCountRealTime/get", produces = "application/json")
    public Integer getOperCountRealTime(@RequestParam(value = "appId") String appId) {
        long timestamp = Utils.getMinuteBeginTimestamp();
        if (redis.hget("appId:" + appId + ":" + String.valueOf(timestamp), "operation") == null) {
            return 0;
        }
        redis.close();
        return Integer.valueOf(redis.hget("appId:" + appId + ":" + String.valueOf(timestamp), "operation"));
    }

    //查询20分钟前到现在的操作趋势
    @RequestMapping(value = "/operTrend/get", produces = "application/json")
    public int[] getOperTrend(@RequestParam(value = "appId") String appId) {
        int[] result = new int[20];
        long timestamp = Utils.getMinuteBeginTimestamp() - 1000L * 60L * 20L;
        for (int i = 0; i < 20; i++) {
            if (redis.hget("appId:" + appId + ":" + String.valueOf(timestamp + i * 60L * 1000L), "operation") == null) {
                result[i] = 0;
            } else {
                result[i] = Integer.valueOf(redis.hget("appId:" + appId + ":" + String.valueOf(timestamp + i * 60L * 1000L), "operation"));
            }
        }
        return result;
    }

    //查询操作来源浏览器占比
    @RequestMapping(value = "/oper/browser/get", produces = "application/json")
    public Map<String, Object> getOperBrowserPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = operationMapper.getVisitId(appId);
        int number[] = new int[7];
        for (String vstid : visitId) {
            if (!vstid.equals("")) {
                String browser[] = visitMapper.getBrowser2(appId, vstid);
                for (int i = 0; i < browser.length; i++) {
                    switch (browser[i]) {
                        case "chrome":
                            number[0]++;
                            break;
                        case "msie":
                            number[1]++;
                            break;
                        case "firefox":
                            number[2]++;
                            break;
                        case "safari":
                            number[3]++;
                            break;
                        case "edge":
                            number[4]++;
                            break;
                        case "opera":
                            number[5]++;
                            break;
                        default:
                            number[6]++;
                            break;
                    }
                }
            }
        }
        int sum = 0;
        for (int j = 0; j < number.length; j++) {
            sum += number[j];
        }
        map.put("chrome", (1.0 * number[0] / sum) * 100);
        map.put("msie", (1.0 * number[1] / sum) * 100);
        map.put("firefox", (1.0 * number[2] / sum) * 100);
        map.put("safari", (1.0 * number[3] / sum) * 100);
        map.put("edge", (1.0 * number[4] / sum) * 100);
        map.put("opera", (1.0 * number[5] / sum) * 100);
        map.put("other", (1.0 * number[6] / sum) * 100);
        return map;
    }


    //查询操作来源运营商占比
    @RequestMapping(value = "/oper/isp/get", produces = "application/json")
    public Map<String, Object> getOperIspPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = operationMapper.getVisitId(appId);
        int number[] = new int[7];
        for (String vstid : visitId) {
            if (!vstid.equals("")) {
                String isp[] = visitMapper.getIsp2(appId, vstid);
                for (int i = 0; i < isp.length; i++) {
                    switch (isp[i]) {
                        case "电信":
                            number[0]++;
                            break;
                        case "联通":
                            number[1]++;
                            break;
                        case "移动":
                            number[2]++;
                            break;
                        default:
                            number[3]++;
                            break;
                    }
                }
            }
        }
        int sum = 0;
        for (int j = 0; j < number.length; j++) {
            sum += number[j];
        }
        map.put("dianxin", (1.0 * number[0] / sum) * 100);
        map.put("liantong", (1.0 * number[1] / sum) * 100);
        map.put("yidong", (1.0 * number[2] / sum) * 100);
        map.put("qita", (1.0 * number[3] / sum) * 100);
        return map;
    }

    //查询操作来源操作系统占比
    @RequestMapping(value = "/oper/os/get", produces = "application/json")
    public Map<String, Object> getOperOsPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = operationMapper.getVisitId(appId);
        int number[] = new int[7];
        for (String vstid : visitId) {
            if (!vstid.equals("")) {
                String os[] = visitMapper.getOs2(appId, vstid);
                for (int i = 0; i < os.length; i++) {
                    switch (os[i]) {
                        case "OS X":
                            number[0]++;
                            break;
                        case "Linux":
                            number[1]++;
                            break;
                        case "Windows XP":
                            number[2]++;
                            break;
                        case "Windows 7":
                            number[3]++;
                            break;
                        case "Windows 8":
                            number[4]++;
                            break;
                        case "Windows 10":
                            number[5]++;
                            break;
                        default:
                            number[6]++;
                            break;
                    }
                }
            }
        }
        int sum = 0;
        for (int j = 0; j < number.length; j++) {
            sum += number[j];
        }
        map.put("OS X", (1.0 * number[0] / sum) * 100);
        map.put("Linux", (1.0 * number[1] / sum) * 100);
        map.put("Windows XP", (1.0 * number[2] / sum) * 100);
        map.put("Windows 7", (1.0 * number[3] / sum) * 100);
        map.put("Windows 8", (1.0 * number[4] / sum) * 100);
        map.put("Windows 10", (1.0 * number[5] / sum) * 100);
        map.put("qita", (1.0 * number[6] / sum) * 100);
        return map;
    }

    //查询操作列表
    @RequestMapping(value = "/operList/get", produces = "application/json")
    public List<Map<String, Object>> getOperList(@RequestParam(value = "appId") String appId) {
        List<Map<String, Object>> list = new ArrayList<>();
        list = operationMapper.getOperInfo(appId);
        for (int i = 0; i < list.size(); i++) {
            String visitId = list.get(i).get("visitId").toString();
            if (!visitId.equals("") && visitId != null) {
                List<Map<String, Object>> visitlist = visitMapper.getOperList(appId, visitId);
                if (visitlist != null && visitlist.size() != 0) {
                    Map<String, Object> map = visitlist.get(0);
                    if (map != null) {
                        switch (list.get(i).get("operType").toString()) {
                            case "A":
                                list.get(i).put("operType", "超链接");
                                break;
                            case "BODY":
                                list.get(i).put("operType", "文本");
                                break;
                            case "INPUT":
                                list.get(i).put("operType", "表单提交");
                                break;
                            default:
                                list.get(i).put("operType", "其他");
                                break;
                        }
                        list.get(i).put("city", map.get("city"));
                        list.get(i).put("os", map.get("os"));
                        list.get(i).put("browser", map.get("browser"));
                        long timestamp = Long.valueOf(list.get(i).get("timestamp").toString());
                        Date date = new Date(timestamp);
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        list.get(i).put("timestamp", sdf.format(date));
                    }
                }
            }
        }
        return list;
    }
}
