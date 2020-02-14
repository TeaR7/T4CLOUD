package com.t4cloud.t.mock.service.impl;

import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.mock.mapper.SysMockMapper;
import com.t4cloud.t.mock.service.ISysMockService;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

/**
 * Mock数据 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-14 
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysMockServiceImpl extends T4ServiceImpl<SysMockMapper, SysMock> implements ISysMockService {

}
