package com.t4cloud.t.system.service.impl;

import com.t4cloud.t.base.annotation.cache.DataRuleCacheable;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.system.entity.SysCompany;
import com.t4cloud.t.system.entity.SysUserCompany;
import com.t4cloud.t.system.mapper.SysCompanyMapper;
import com.t4cloud.t.system.mapper.SysUserCompanyMapper;
import com.t4cloud.t.system.service.ISysUserCompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户公司表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserCompanyServiceImpl extends T4ServiceImpl<SysUserCompanyMapper, SysUserCompany> implements ISysUserCompanyService {

    @Autowired
    private final SysCompanyMapper sysCompanyMapper;

    /**
     * 获取某用户的所有公司
     *
     * @param userId 用户ID
     *               <p>
     * @return java.util.List<com.t4cloud.t.system.entity.SysCompany>
     * --------------------
     * @author TeaR
     * @date 2020/4/15 14:11
     */
    @Override
    @DataRuleCacheable
    public List<SysCompany> getUserCompanyList(String userId) {
        return sysCompanyMapper.queryByUserId(userId);
    }

}
