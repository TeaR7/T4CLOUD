package com.t4cloud.t.third.service.impl;

import com.t4cloud.t.third.entity.ThirdConfig;
import com.t4cloud.t.third.mapper.ThirdConfigMapper;
import com.t4cloud.t.third.service.IThirdConfigService;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

/**
 * 第三方API秘钥配置 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-26 
 */
@Slf4j
@Service
@AllArgsConstructor
public class ThirdConfigServiceImpl extends T4ServiceImpl<ThirdConfigMapper, ThirdConfig> implements IThirdConfigService {


}
