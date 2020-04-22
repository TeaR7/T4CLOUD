package com.t4cloud.t.system.mapper;


import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t4cloud.t.system.entity.SysCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司表 Mapper 接口
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
public interface SysCompanyMapper extends BaseMapper<SysCompany> {

    /**
     * 根据用户查询用户权限
     */
    @SqlParser(filter = true)
    List<SysCompany> queryByUserId(@Param("userId") String userId);

}
