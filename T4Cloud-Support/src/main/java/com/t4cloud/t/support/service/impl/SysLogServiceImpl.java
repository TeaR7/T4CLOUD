package com.t4cloud.t.support.service.impl;

import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.support.entity.SysLog;
import com.t4cloud.t.support.mapper.SysLogMapper;
import com.t4cloud.t.support.service.ISysLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-27
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysLogServiceImpl extends T4ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {


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
    @Override
    public Integer countVisit(Date start, Date end) {
        Integer count = this.lambdaQuery().eq(SysLog::getLogType, 2).eq(SysLog::getOperateType, 1)
                .ge(start != null, SysLog::getCreateTime, start)
                .le(end != null, SysLog::getCreateTime, end)
                .count();
        return count;
    }

    /**
     * 统计系统访问IP
     *
     * <p>
     *
     * @param start
     * @param end
     * @return java.lang.Integer
     * --------------------
     * @author TeaR
     * @date 2020/3/27 17:56
     */
    @Override
    public Integer countIp(Date start, Date end) {
        return baseMapper.countIp(start, end);
    }

    /**
     * 访问统计折线图
     *
     * <p>
     *
     * @param start
     * @param end
     * @return java.lang.Integer
     * --------------------
     * @author TeaR
     * @date 2020/3/27 17:56
     */
    @Override
    public List<Map<String, Object>> countDailyVisitInfo(Date start, Date end) {
        return baseMapper.countDailyVisitInfo(start, end);
    }
}
