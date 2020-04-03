package com.t4cloud.t.support.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.support.entity.SysLog;
import com.t4cloud.t.support.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Support 数据统计 控制器
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
public class DashboardController extends T4Controller<SysLog, ISysLogService> {


    /**
     * 访问量统计
     */
    @AutoLog(value = "访问量统计")
    @GetMapping("/visitInfo")
    @ApiOperation(position = 1, value = "访问量统计", notes = "访问量统计、提供用户注册数据，系统访问数据等")
    public R visitInfo() {

        //上方访问量数据
        JSONObject result = new JSONObject();

        // 获取一天的开始和结束时间
        Date start = DateUtil.beginOfDay(DateUtil.date());
        Date end = DateUtil.endOfDay(DateUtil.date());

        // 获取系统访问记录
        Integer totalVisit = service.countVisit(null, null);
        result.put("totalVisit", totalVisit);
        Integer todayVisit = service.countVisit(start, end);
        result.put("todayVisit", todayVisit);
        Integer todayIp = service.countIp(start, end);
        result.put("todayIp", todayIp);

        // 获取访问记录折线图数据
        List<Map<String, Object>> dailyVisitInfo = service.countDailyVisitInfo(DateUtil.beginOfMonth(start), end);
        result.put("dailyVisitInfo", dailyVisitInfo);

        return R.ok("访问量统计获取成功！", result);
    }

}
