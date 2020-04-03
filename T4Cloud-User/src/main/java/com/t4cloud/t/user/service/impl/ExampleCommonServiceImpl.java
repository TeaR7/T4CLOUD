package com.t4cloud.t.user.service.impl;

import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.user.entity.ExampleCommon;
import com.t4cloud.t.user.mapper.ExampleCommonMapper;
import com.t4cloud.t.user.service.IExampleCommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通用示例 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExampleCommonServiceImpl extends T4ServiceImpl<ExampleCommonMapper, ExampleCommon> implements IExampleCommonService {


}
