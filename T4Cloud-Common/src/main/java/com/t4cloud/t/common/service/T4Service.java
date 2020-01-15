package com.t4cloud.t.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 基础服务类，用来扩展提供的基础方法
 *
 * <p>
 * --------------------
 * @author TeaR
 * @date 2020/1/15 23:35
 */
public interface T4Service<T> extends IService<T> {

    /** TODO 以下只是为了占位，暂时还没有实现
     *
     * -by TeaR  -2020/1/15-23:41
     */
    boolean deleteLogic(@NotEmpty List<Long> ids);

    boolean changeStatus(@NotEmpty List<Long> ids, Integer status);

}
