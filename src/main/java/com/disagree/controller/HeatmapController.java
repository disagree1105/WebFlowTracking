package com.disagree.controller;

import com.disagree.mapper.HeatmapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by disagree on 2017/5/6.
 */
@RestController
public class HeatmapController {
    @Autowired
    HeatmapMapper heatmapMapper;

    //查询应用概况最上面4个
    @RequestMapping(value = "/heatmap/get", produces = "application/json")
    public List<Map<String, Object>> getHeatmap(@RequestParam(value = "appId") String appId) {
        return heatmapMapper.getHeatMap(appId);
    }
}
