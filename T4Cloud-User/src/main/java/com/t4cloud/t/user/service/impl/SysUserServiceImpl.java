package com.t4cloud.t.user.service.impl;

import com.t4cloud.t.common.service.impl.T4ServiceImpl;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.mapper.SysUserMapper;
import com.t4cloud.t.user.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @Author: scott
 * @Date: 2018-12-20
 */
@Service
@Slf4j
@AllArgsConstructor
public class SysUserServiceImpl extends T4ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {



}
