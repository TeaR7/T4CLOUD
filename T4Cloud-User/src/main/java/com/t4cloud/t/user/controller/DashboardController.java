package com.t4cloud.t.user.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User相关 数据统计 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-03-27
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
@Api(value = "访问统计等数据接口", tags = "数据统计", position = 0)
public class DashboardController extends T4Controller<SysUser, ISysUserService> {


    /**
     * 用户量统计 （注册量）
     */
    @AutoLog(value = "注册量统计")
    @GetMapping("/registryInfo")
    @ApiOperation(position = 1, value = "注册量统计", notes = "注册量统计、提供用户近期注册量、总量、周期环比、注册曲线等")
    public R registryInfo(
            @ApiParam("开始时间")
            @RequestParam(name = "start", required = false) String start,
            @ApiParam("结束时间")
            @RequestParam(name = "end", required = false) String end
    ) {

        //结果集
        JSONObject data = new JSONObject();

        Date startDate;
        Date endDate;
        //如果不传start或end 默认比较14天内
        if (ObjectUtil.isEmpty(start) || ObjectUtil.isEmpty(end)) {
            Date now = DateUtil.date();
            startDate = DateUtil.offsetDay(DateUtil.beginOfDay(now), -13);
            endDate = now;
        } else {
            startDate = DateUtil.parseDateTime(start);
            endDate = DateUtil.parseDateTime(end);
        }
        if (startDate.compareTo(endDate) > 0) {
            return R.error("开始时间不能大于结束时间");
        }

        //N天每天的注册量
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.select("DATE_FORMAT(create_time,'%Y-%m-%d') create_time,count(1) cc ");
        queryWrapper.ge("create_time", startDate);
        queryWrapper.le("create_time", DateUtil.endOfDay(endDate));
        queryWrapper.groupBy("DATE_FORMAT(create_time,'%Y-%m-%d')");
        queryWrapper.orderByAsc("DATE_FORMAT(create_time,'%Y-%m-%d')");
        List<Map<String, Object>> list = service.listMaps(queryWrapper);

        JSONArray dayCount = new JSONArray();
        for (int i = 0; i <= DateUtil.betweenDay(startDate, endDate, true); i++) {
            Date dateTmp;
            if (i == 0) {
                dateTmp = startDate;
            } else {
                dateTmp = DateUtil.offsetDay(startDate, i);
            }
            JSONObject tmp = new JSONObject();
            tmp.put("x", DateUtil.format(dateTmp, "YYYY.MM.dd"));
            tmp.put("y", 0);
            for (Map<String, Object> item : list) {
                if (DateUtil.format(dateTmp, "YYYY-MM-dd").equals(item.get("create_time"))) {
                    tmp.put("y", Integer.parseInt(item.get("cc").toString()));
                }
            }
            dayCount.add(tmp);
        }

        //放入结果集
        data.put("dayCount", dayCount);


        //总用户数
        queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("status", 1);
        int total = service.count(queryWrapper);
        //放入结果集
        data.put("total", total);

        //最近注册用户数
        queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("status", 1);
        queryWrapper.ge("create_time", startDate);
        queryWrapper.le("create_time", DateUtil.endOfDay(endDate));
        int lastRegistry = service.count(queryWrapper);
        //放入结果集
        data.put("lastRegistry", lastRegistry);

        //上一个N天增长用户数
        long offset = DateUtil.betweenDay(startDate, endDate, true) + 1;
        queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("status", 1);
        queryWrapper.ge("create_time", DateUtil.offsetDay(startDate, Integer.parseInt(String.valueOf(-offset))));
        queryWrapper.lt("create_time", startDate);
        int preRegistry = service.count(queryWrapper);
        //放入结果集
        data.put("lastRegistryTrend", lastRegistry >= preRegistry ? "up" : "down");

        //今天注册用户数
        queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("status", 1);
        queryWrapper.ge("create_time", DateUtil.beginOfDay(DateUtil.date()));
        queryWrapper.lt("create_time", DateUtil.endOfDay(DateUtil.date()));
        int todayRegistry = service.count(queryWrapper);
        //放入结果集
        data.put("todayRegistry", todayRegistry);

        //昨天注册用户数
        queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("status", 1);
        queryWrapper.ge("create_time", DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), -1)));
        queryWrapper.lt("create_time", DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(), -1)));
        int yesterdayRegistry = service.count(queryWrapper);
        //放入结果集
        data.put("todayRegistryTrend", todayRegistry >= yesterdayRegistry ? "up" : "down");

        return R.ok("注册量统计成功！", data);
    }

}
