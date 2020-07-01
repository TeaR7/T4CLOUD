package com.t4cloud.t.third.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.third.entity.ThirdConfig;
import com.t4cloud.t.third.service.IThirdConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 第三方API秘钥配置 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-26
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "第三方API秘钥配置", tags = "第三方API秘钥配置接口", position = 10)
@RequestMapping("/ThirdConfig")
public class ThirdConfigController extends T4Controller<ThirdConfig, IThirdConfigService> {

    /**
     * 详情
     */
    @AutoLog(value = "第三方API秘钥配置-详情")
    @GetMapping("/detail")
    @RequiresPermissions("third:ThirdConfig:VIEW")
    @ApiOperation(position = 1, value = "第三方API秘钥配置-详情", notes = "传入thirdConfig")
    public R<ThirdConfig> detail(ThirdConfig thirdConfig, HttpServletRequest req) {
        QueryWrapper<ThirdConfig> thirdConfigQueryWrapper = T4Query.initQuery(thirdConfig, req.getParameterMap());
        ThirdConfig detail = service.getOne(thirdConfigQueryWrapper);
        return R.ok("第三方API秘钥配置-详情查询成功", detail);
    }

    /**
     * 全部列表 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("third:ThirdConfig:VIEW")
    @ApiOperation(position = 2, value = "第三方API秘钥配置-全部列表", notes = "传入thirdConfig")
    public R<List<ThirdConfig>> list(ThirdConfig thirdConfig, HttpServletRequest req) {
        QueryWrapper<ThirdConfig> thirdConfigQueryWrapper = T4Query.initQuery(thirdConfig, req.getParameterMap());
        List<ThirdConfig> list = service.list(thirdConfigQueryWrapper);
        return R.ok("第三方API秘钥配置-全部列表查询成功", list);
    }

    /**
     * 分页查询 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("third:ThirdConfig:VIEW")
    @ApiOperation(position = 3, value = "第三方API秘钥配置-分页查询", notes = "传入thirdConfig")
    public R<IPage<ThirdConfig>> page(ThirdConfig thirdConfig,
                                      @ApiParam(name = "pageNo")
                                      @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                      @ApiParam(name = "pageSize")
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        QueryWrapper<ThirdConfig> thirdConfigQueryWrapper = T4Query.initQuery(thirdConfig, req.getParameterMap());
        IPage<ThirdConfig> pages = service.page(new Page<>(pageNo, pageSize), thirdConfigQueryWrapper);
        return R.ok("第三方API秘钥配置-分页查询查询成功", pages);
    }

    /**
     * 新增 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("third:ThirdConfig:ADD")
    @ApiOperation(position = 4, value = "第三方API秘钥配置-新增", notes = "传入thirdConfig")
    public R save(@Valid @RequestBody ThirdConfig thirdConfig, BindingResult bindingResult) {
        return R.ok("第三方API秘钥配置-新增成功", service.save(thirdConfig));
    }

    /**
     * 修改 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"third:ThirdConfig:ADD", "third:ThirdConfig:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "第三方API秘钥配置-修改", notes = "传入thirdConfig")
    public R update(@RequestBody ThirdConfig thirdConfig) {
        return R.ok("第三方API秘钥配置-修改成功", service.updateById(thirdConfig));
    }


    /**
     * 删除 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("third:ThirdConfig:DELETE")
    @ApiOperation(position = 8, value = "第三方API秘钥配置-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("第三方API秘钥配置-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-导出")
    @GetMapping("/export")
    @RequiresPermissions("third:ThirdConfig:EXPORT")
    @ApiOperation(position = 9, value = "第三方API秘钥配置-导出", notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(ThirdConfig thirdConfig,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys", required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys", required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<ThirdConfig> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(thirdConfig, req.getParameterMap()) : new QueryWrapper<ThirdConfig>().in("id", selectedRowKeys.split(","));
        super.exportExl("第三方API秘钥配置", selectedColKeys, queryWrapper);
    }

    /**
     * 导入 第三方API秘钥配置
     */
    @AutoLog(value = "第三方API秘钥配置-导入", operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("third:ThirdConfig:IMPORT")
    @ApiOperation(position = 10, value = "第三方API秘钥配置-导入", notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("第三方API秘钥配置-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, ThirdConfig.class)));
    }

    @AutoLog(value = "第三方API秘钥配置-确认重复信息")
    @GetMapping("/check")
    @RequiresPermissions("third:ThirdConfig:ADD")
    @ApiOperation(position = 11, value = "第三方API秘钥配置-确认重复信息", notes = "传入查询属性名和属性值")
    public R check(
            @ApiParam("属性名") @RequestParam("key") String key,
            @ApiParam("属性值") @RequestParam("value") String value) {
        Boolean checkProp = checkProp(key, value);
        return R.ok(key + "：" + value + (checkProp ? "可用，无重名项" : "不可用，存在冲突"), checkProp);
    }

}
