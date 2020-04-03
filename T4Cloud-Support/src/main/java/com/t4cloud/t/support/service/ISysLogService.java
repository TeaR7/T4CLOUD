package com.t4cloud.t.support.service;


import com.t4cloud.t.base.service.T4Service;
import com.t4cloud.t.support.entity.SysLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志表 服务类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-27
 */
public interface ISysLogService extends T4Service<SysLog> {

    /**
     * 统计系统总访问量
     *
     * <p>
     *
     * @return java.lang.Long
     * --------------------
     * @author TeaR
     * @date 2020/3/27 17:43
     */
    Integer countVisit(Date start, Date end);

    /**
     * 统计系统访问IP
     *
     * <p>
     *
     * @return java.lang.Integer
     * --------------------
     * @author TeaR
     * @date 2020/3/27 17:56
     */
    Integer countIp(Date start, Date end);

    /**
     * 访问统计折线图
     *
     * <p>
     *
     * @return java.lang.Integer
     * --------------------
     * @author TeaR
     * @date 2020/3/27 17:56
     */
    List<Map<String, Object>> countDailyVisitInfo(Date start, Date end);

}
