package com.t4cloud.t.support.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4cloud.t.support.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志表 Mapper 接口
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-27
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

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
    Integer countIp(@Param("start") Date start, @Param("end") Date end);

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
    List<Map<String, Object>> countDailyVisitInfo(@Param("start") Date start, @Param("end") Date end);


}
