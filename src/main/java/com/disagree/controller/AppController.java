package com.disagree.controller;

import com.disagree.mapper.*;
import com.disagree.utils.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by disagree on 2017/4/25.
 */
@RestController
public class AppController {
    @Autowired
    ApplicationMapper applicationMapper;

    @Autowired
    ErrorMapper errorMapper;

    @Autowired
    VisitMapper visitMapper;

    @Autowired
    HeatmapMapper heatmapMapper;

    @Autowired
    OperationMapper operationMapper;

    //查询所有应用及其面板信息
    @RequestMapping(value = "/appInfo/get", produces = "application/json")
    public List<Map<String, Object>> getAppInfo(@RequestParam(value = "uid") String uid) {
        List<Map<String, Object>> list = applicationMapper.getAppInfo(uid);
        for (Map<String, Object> map : list) {
            String appId = map.get("appId").toString();
            map.put("errorCount", errorMapper.countError(appId));
            map.put("visitCount", visitMapper.countVisit(appId));
            map.put("operCount", operationMapper.countOperation(appId));
        }
        return list;
    }

    //查询应用概况最上面4个
    @RequestMapping(value = "/today/appInfo/get", produces = "application/json")
    public List getTodayAppInfo(@RequestParam(value = "appId") String appId) {
        List list = new ArrayList();
        int visit = visitMapper.countVisit(appId);
        list.add(visit);
        int operation = operationMapper.countOperation(appId);
        list.add(operation);
        int error = errorMapper.countError(appId);
        list.add(error);
        Integer avgRspTime = visitMapper.avgRspTime(appId);
        list.add(avgRspTime == null ? 0 : avgRspTime);
        return list;
    }

    //查询今日概况中的浏览分时趋势图
    @RequestMapping(value = "/today/visitTrend/get", produces = "application/json")
    public Map<String, Object> getTodayVisitTrend(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        countToday(appId, map, visitMapper);
        countYesterday(appId, map, visitMapper);
        countLast30Day(appId, map, visitMapper);
        return map;
    }

    //查询今日概况中的操作分时趋势图
    @RequestMapping(value = "/today/operTrend/get", produces = "application/json")
    public Map<String, Object> getTodayOperTrend(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        countToday(appId, map, operationMapper);
        countYesterday(appId, map, operationMapper);
        countLast30Day(appId, map, operationMapper);
        return map;
    }

    private void countToday(String appId, Map<String, Object> map, Object mapper) {
        List list = new ArrayList();
        long start = Utils.getDayBeginTimestamp();
        long end = System.currentTimeMillis();
        long temp = start;
        while (temp <= end) {
            if (mapper.getClass() == visitMapper.getClass())
                list.add(visitMapper.countVisitByTime(appId, temp, temp + 60 * 60 * 1000));
            else
                list.add(operationMapper.countOperationByTime(appId, temp, temp + 60 * 60 * 1000));
            temp += 60 * 60 * 1000;
        }
        map.put("today", list);
    }

    private void countYesterday(String appId, Map<String, Object> map, Object mapper) {
        List list = new ArrayList();
        long start = Utils.getDayBeginTimestamp() - 24 * 60 * 60 * 1000;
        long end = Utils.getDayBeginTimestamp();
        long temp = start;
        while (temp < end) {
            if (mapper.getClass() == visitMapper.getClass())
                list.add(visitMapper.countVisitByTime(appId, temp, temp + 60 * 60 * 1000));
            else
                list.add(operationMapper.countOperationByTime(appId, temp, temp + 60 * 60 * 1000));
            temp += 60 * 60 * 1000;
        }
        map.put("yesterday", list);
    }

    private void countLast30Day(String appId, Map<String, Object> map, Object mapper) {
        List list = new ArrayList();
        for (long i = 0L; i < 24L; i++) {
            int sum = 0;
            for (long j = 30L; j > 0L; j--) {
                long offset = i * 3600L * 1000L;
                long offset2 = (i + 1) * 3600L * 1000L;
                long dayPast = j * 24L * 3600L * 1000L;
                long start = Utils.getDayBeginTimestamp() + offset - dayPast;
                long end = Utils.getDayBeginTimestamp() + offset2 - dayPast;
                if (mapper.getClass() == visitMapper.getClass())
                    sum += visitMapper.countVisitByTime(appId, start, end);
                else
                    sum += operationMapper.countOperationByTime(appId, start, end);
            }
            list.add(sum / 30);
        }
        map.put("lastMonth", list);
    }

    //用户分布图
    @RequestMapping(value = "/userMap/get", produces = "application/json")
    public Map<String, Object> getUserMap(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String[] str;
        str = visitMapper.getCityCode(appId);
        int[] data = new int[34];
        for (int i = 0; i < str.length; i++) {
            switch (str[i].substring(0, 2).toString()) {
                case "11":
                    data[0] += 1;
                    break;
                case "12":
                    data[1] += 1;
                    break;
                case "13":
                    data[4] += 1;
                    break;
                case "14":
                    data[18] += 1;
                    break;
                case "15":
                    data[19] += 1;
                    break;
                case "21":
                    data[7] += 1;
                    break;
                case "22":
                    data[22] += 1;
                    break;
                case "23":
                    data[8] += 1;
                    break;
                case "31":
                    data[2] += 1;
                    break;
                case "32":
                    data[13] += 1;
                    break;
                case "33":
                    data[12] += 1;
                    break;
                case "34":
                    data[10] += 1;
                    break;
                case "35":
                    data[23] += 1;
                    break;
                case "36":
                    data[20] += 1;
                    break;
                case "37":
                    data[11] += 1;
                    break;
                case "41":
                    data[5] += 1;
                    break;
                case "42":
                    data[15] += 1;
                    break;
                case "43":
                    data[9] += 1;
                    break;
                case "44":
                    data[25] += 1;
                    break;
                case "45":
                    data[16] += 1;
                    break;
                case "51":
                    data[28] += 1;
                    break;
                case "52":
                    data[24] += 1;
                    break;
                case "53":
                    data[6] += 1;
                    break;
                case "54":
                    data[27] += 1;
                    break;
                case "61":
                    data[21] += 1;
                    break;
                case "62":
                    data[17] += 1;
                    break;
                case "63":
                    data[29] += 1;
                    break;
                case "65":
                    data[14] += 1;
                    break;
                case "71":
                    data[31] += 1;
                    break;
                case "81":
                    data[32] += 1;
                    break;
                case "82":
                    data[33] += 1;
                    break;

            }

        }
        map.put("beijing", data[0]);
        map.put("tianjin", data[1]);
        map.put("shanghai", data[2]);
        map.put("chongqin", data[3]);
        map.put("hebei", data[4]);
        map.put("henan", data[5]);
        map.put("yunnan", data[6]);
        map.put("liaoning", data[7]);
        map.put("heilongjiang", data[8]);
        map.put("hunan", data[9]);
        map.put("anhui", data[10]);
        map.put("shandong", data[11]);
        map.put("zhejiang", data[12]);
        map.put("jiangsu", data[13]);
        map.put("xinjiang", data[14]);
        map.put("hubei", data[15]);
        map.put("guangxi", data[16]);
        map.put("gansu", data[17]);
        map.put("shanxi1", data[18]);
        map.put("neimenggu", data[19]);
        map.put("jiangxi", data[20]);
        map.put("shanxi3", data[21]);
        map.put("jilin", data[22]);
        map.put("fujian", data[23]);
        map.put("guizhou", data[24]);
        map.put("guangdong", data[25]);
        map.put("qinghai", data[26]);
        map.put("xizang", data[27]);
        map.put("sichuan", data[28]);
        map.put("ningxia", data[29]);
        map.put("hainan", data[30]);
        map.put("taiwan", data[31]);
        map.put("xianggang", data[32]);
        map.put("aomen", data[33]);

        return map;
    }

    //查询apdex及百分比
    @RequestMapping(value = "/apdex/get", produces = "application/json")
    public Map<String, Object> getApdex(@RequestParam(value = "appId") String appId) {
        Integer apdexT = applicationMapper.getApdexT(appId);
        int satisfy = visitMapper.countSatisfy(appId, apdexT);
        int tolerate = visitMapper.countTolerate(appId, apdexT, apdexT * 4);
        int disappoint = visitMapper.countDisappoint(appId, apdexT * 4);
        int sum = satisfy + tolerate + disappoint;
        double apdex = (satisfy + 0.5 * tolerate) / sum;
        Map<String, Object> map = new HashedMap();
        map.put("apdex", (int) Math.rint(apdex * 100));
        int persentS = (int) Math.rint((satisfy * 1.0 / sum) * 100);
        int persentT = (int) Math.rint((tolerate * 1.0 / sum) * 100);
        int persentD = 100 - persentS - persentT;
        map.put("persentS", persentS);
        map.put("persentT", persentT);
        map.put("persentD", persentD);
        return map;
    }

    //查询浏览器占比
    @RequestMapping(value = "/browser/get", produces = "application/json")
    public Map<String, Object> getBrowserPercentage(@RequestParam(value = "appId") String appId) {
        String result[] = visitMapper.getBrowser(appId);
        Map<String, Object> map = new HashedMap();
        int[] number = new int[7];
        number = getBrowserPercentage(result, number);
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

    public static int[] getBrowserPercentage(String result[], int number[]) {
        for (int i = 0; i < result.length; i++) {
            switch (result[i]) {
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
        return number;
    }


    //查询运营商占比
    @RequestMapping(value = "/isp/get", produces = "application/json")
    public Map<String, Object> getIspPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String result[] = visitMapper.getIsp(appId);
        int[] number = new int[4];
        for (int i = 0; i < result.length; i++) {
            switch (result[i]) {
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

    //查询操作系统占比
    @RequestMapping(value = "/os/get", produces = "application/json")
    public Map<String, Object> getOsPercentage(@RequestParam(value = "appId") String appId) {
        Map<String, Object> map = new HashedMap();
        String result[] = visitMapper.getOs(appId);
        int[] number = new int[7];
        for (int i = 0; i < result.length; i++) {
            switch (result[i]) {
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


    //添加应用
    @RequestMapping(value = "/app/create", produces = "application/json")
    public String createApp(@RequestParam(value = "uid") String uid,
                            @RequestParam(value = "appName") String appName,
                            @RequestParam(value = "url") String url) {
        String appId = UUID.randomUUID().toString();
        int apdexT = 2000;
        applicationMapper.createApp(appId, uid, appName, url, apdexT);
        return Utils.getResult(1, 1, "添加成功！", null);
    }

    @RequestMapping(value = "/app/delete", produces = "application/json")
    public String deleteApp(@RequestParam(value = "appId") String appId) {
        applicationMapper.deleteApp(appId);
        errorMapper.delete(appId);
        heatmapMapper.delete(appId);
        operationMapper.delete(appId);
        visitMapper.delete(appId);
        return Utils.getResult(1, 1, "删除成功！", null);
    }

    @RequestMapping(value = "/app/update", produces = "application/json")
    public String updateApp(@RequestParam(value = "appId") String appId,
                            @RequestParam(value = "appName") String appName,
                            @RequestParam(value = "url") String url) {
        applicationMapper.updateApp(appId, appName, url);
        return Utils.getResult(1, 1, "更新成功！", null);
    }

    @RequestMapping(value = "/app/config/update",produces = "application/josn")
    public String updateAppConfig(@RequestParam(value = "appId") String appId,
                                  @RequestParam(value = "apdexT") String apdexT,
                                  @RequestParam(value = "dataSaveTime") String dataSaveTime)
    {
        applicationMapper.updateAppConfig(appId,apdexT,dataSaveTime);
        return Utils.getResult(1, 1, "更新成功！", null);
    }

    @RequestMapping(value = "/app/config/get",produces = "application/json")
    public Map<String,Object> getAppConfig(@RequestParam(value = "appId") String appId)
    {
        return applicationMapper.getAppConfig(appId);
    }

    //获取应用url by appId
    @RequestMapping(value = "/appUrl/get", produces = "application/html")
    public String getAppUrl(@RequestParam(value = "appId") String appId) {
        return applicationMapper.getAppUrl(appId);
    }

}
