package com.disagree.controller;

import com.disagree.mapper.ErrorMapper;
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
 * Created by disagree on 2017/5/1.
 */
@RestController
public class ErrorController {

    @Autowired
    ErrorMapper errorMapper;

    @Autowired
    VisitMapper visitMapper;

    Jedis redis = new Jedis("localhost", 6379);

    //查询某应用的错误实时数据
    @RequestMapping(value = "/errorCountRealTime/get", produces = "application/json")
    public Integer getErrorCountRealTime(@RequestParam(value = "appId") String appId) {
//        long end = Utils.getMinuteBeginTimestamp();
//        long start = end - 60 * 1000;
//        System.out.println("查询的是从" + start + "到" + end + "的时间戳");
//        return errorMapper.countErrorByTime(appId, start, end);
        long timestamp = Utils.getMinuteBeginTimestamp();
        if (redis.hget("appId:" + appId + ":" +String.valueOf(timestamp), "error") == null)
            return 0;
        redis.close();
        return Integer.valueOf(redis.hget("appId:" + appId + ":" +String.valueOf(timestamp), "error"));
    }

    //查询20分钟前到现在的错误趋势
    @RequestMapping(value = "/errorTrend/get", produces = "application/json")
    public int[] getErrorTrend(@RequestParam(value = "appId") String appId) {
        int[] result = new int[20];
        long timestamp = Utils.getMinuteBeginTimestamp() - 1000L * 60L * 20L;
        for (int i = 0; i < 20; i++) {
            if (redis.hget("appId:" + appId + ":" +String.valueOf(timestamp + i * 60L * 1000L), "error") == null)
                result[i] = 0;
            else
                result[i] = Integer.valueOf(redis.hget("appId:" + appId + ":" +String.valueOf(timestamp + i * 60L * 1000L), "error"));
        }
        return result;
    }

    //查询错误来源浏览器占比
    @RequestMapping(value = "/error/browser/get", produces = "application/json")
    public Map<String, Object> getErrorBrowserPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = errorMapper.getVisitId(appId);
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
    @RequestMapping(value = "/error/isp/get", produces = "application/json")
    public Map<String, Object> getErrorIspPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = errorMapper.getVisitId(appId);
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
    @RequestMapping(value = "/error/os/get", produces = "application/json")
    public Map<String, Object> getErrorOsPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String visitId[] = errorMapper.getVisitId(appId);
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

    //查询错误列表
    @RequestMapping(value = "errorList/get", produces = "application/json")
    public List<Map<String, Object>> getErrorList(@RequestParam(value = "appId") String appId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] errors = errorMapper.getErrorMessage(appId);
        for (String error : errors) {
            if (error != null && !error.equals("")) {
                Map<String, Object> map = new HashedMap();
                map.put("errorMessage", error);
                String[] errorIds = errorMapper.getErrorId(error);
                map.put("errorId", errorIds[0]);
                map.put("errorCount", errorMapper.countSameError(appId, error));
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(errorMapper.getMaxTime(appId, error));
                map.put("lastError", sdf.format(date));
                list.add(map);
            }
        }
        return list;
    }

    //查询错误详情
    @RequestMapping(value = "errorInfo/get", produces = "application/json")
    public List<Map<String, Object>> getErrorInfo(@RequestParam(value = "appId") String appId,
                                                  @RequestParam(value = "errorMessage") String errorMessage) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> info = errorMapper.getErrorInfo(appId, errorMessage);
        for (int i = 0; i < info.size(); i++) {
            Map<String, Object> map = new HashedMap();
            String visitId = info.get(i).get("visitId").toString();
            List<Map<String, Object>> list2 = visitMapper.getOperList(appId, visitId);
            Map<String, Object> osAndBrowser = list2.get(0);
            map.put("scriptURI", info.get(i).get("scriptURI").toString());
            map.put("columnNumber", info.get(i).get("columnNumber").toString());
            map.put("lineNumber", info.get(i).get("lineNumber").toString());
            map.put("os", osAndBrowser.get("os").toString());
            map.put("browser", osAndBrowser.get("browser").toString());
            map.put("errorMessage", info.get(i).get("errorMessage").toString());
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.valueOf(info.get(i).get("timestamp").toString()));
            map.put("timestamp", sdf.format(date));
            list.add(map);
        }
        return list;
    }


}
