package com.t4cloud.t.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t4cloud.t.common.entity.BaseEntity;
import com.t4cloud.t.common.service.T4Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类基类 实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 23:40
 */
@Validated
public class T4ServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements T4Service<T> {

    private Class<T> modelClass;

    public boolean deleteLogic(@NotEmpty List<Long> ids) {
        return true;
    }

    public boolean changeStatus(@NotEmpty List<Long> ids, Integer status) {
        return true;
    }
}
