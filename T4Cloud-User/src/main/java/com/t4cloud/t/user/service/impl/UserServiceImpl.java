package com.t4cloud.t.user.service.impl;

import com.t4cloud.t.user.entity.User;
import com.t4cloud.t.user.mapper.UserMapper;
import com.t4cloud.t.user.service.IUserService;
import com.t4cloud.t.common.service.impl.T4ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

/**
 * 用户表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-08 
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends T4ServiceImpl<UserMapper, User> implements IUserService {

}
