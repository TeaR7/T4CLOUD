package com.t4cloud.t.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.DataRuleCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.system.entity.SysCompany;
import com.t4cloud.t.system.service.ISysCompanyService;
import com.t4cloud.t.system.service.ISysUserCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.t4cloud.t.base.constant.CacheConstant.SYS_USERS_COMPANY_CACHE;

/**
 * 公司表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "公司表" , tags = "公司表接口" , position = 10)
@RequestMapping("/SysCompany")
public class SysCompanyController extends T4Controller<SysCompany, ISysCompanyService> {

    @Autowired
    private ISysUserCompanyService userCompanyService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 详情
     */
    @AutoLog(value = "公司表-详情")
    @GetMapping("/detail")
    @RequiresPermissions("system:SysCompany:VIEW")
    @ApiOperation(position = 1, value = "公司表-详情" , notes = "传入sysCompany")
    public R<SysCompany> detail(SysCompany sysCompany, HttpServletRequest req) {
        QueryWrapper<SysCompany> sysCompanyQueryWrapper = T4Query.initQuery(sysCompany, req.getParameterMap());
        SysCompany detail = service.getOne(sysCompanyQueryWrapper);
        return R.ok("公司表-详情查询成功" , detail);
    }

    /**
     * 全部列表 公司表
     */
    @AutoLog(value = "公司表-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("system:SysCompany:VIEW")
    @ApiOperation(position = 2, value = "公司表-全部列表" , notes = "传入sysCompany")
    public R<List<SysCompany>> list(SysCompany sysCompany, HttpServletRequest req) {
        QueryWrapper<SysCompany> sysCompanyQueryWrapper = T4Query.initQuery(sysCompany, req.getParameterMap());
        List<SysCompany> list = service.list(sysCompanyQueryWrapper);
        return R.ok("公司表-全部列表查询成功" , list);
    }

    /**
     * 分页查询 公司表
     */
    @AutoLog(value = "公司表-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("system:SysCompany:VIEW")
    @ApiOperation(position = 3, value = "公司表-分页查询" , notes = "传入sysCompany")
    public R<IPage<SysCompany>> page(SysCompany sysCompany,
                                     @ApiParam(name = "pageNo")
                                     @RequestParam(name = "pageNo" , required = false, defaultValue = "1") Integer pageNo,
                                     @ApiParam(name = "pageSize")
                                     @RequestParam(name = "pageSize" , required = false, defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {
        QueryWrapper<SysCompany> sysCompanyQueryWrapper = T4Query.initQuery(sysCompany, req.getParameterMap());
        IPage<SysCompany> pages = service.page(new Page<>(pageNo, pageSize), sysCompanyQueryWrapper);
        return R.ok("公司表-分页查询查询成功" , pages);
    }

    /**
     * 新增 公司表
     */
    @AutoLog(value = "公司表-新增" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysCompany:ADD")
    @ApiOperation(position = 4, value = "公司表-新增" , notes = "传入sysCompany")
    public R save(@Valid @RequestBody SysCompany sysCompany, BindingResult bindingResult) {
        return R.ok("公司表-新增成功" , service.save(sysCompany));
    }

    /**
     * 修改 公司表
     */
    @AutoLog(value = "公司表-修改" , operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:SysCompany:ADD" , "system:SysCompany:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "公司表-修改" , notes = "传入sysCompany")
    @DataRuleCacheEvict
    public R update(@RequestBody SysCompany sysCompany) {
        return R.ok("公司表-修改成功" , service.updateById(sysCompany));
    }


    /**
     * 删除 公司表
     */
    @AutoLog(value = "公司表-删除" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("system:SysCompany:DELETE")
    @DataRuleCacheEvict
    @ApiOperation(position = 8, value = "公司表-删除" , notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合" , required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("公司表-删除成功" , service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 公司表
     */
    @AutoLog(value = "公司表-导出")
    @GetMapping("/export")
    @RequiresPermissions("system:SysCompany:EXPORT")
    @ApiOperation(position = 9, value = "公司表-导出" , notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SysCompany sysCompany,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys" , required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys" , required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysCompany> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysCompany, req.getParameterMap()) : new QueryWrapper<SysCompany>().in("id" , selectedRowKeys.split(","));
        super.exportExl("公司表" , selectedColKeys, queryWrapper);
    }

    /**
     * 导入 公司表
     */
    @AutoLog(value = "公司表-导入" , operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("system:SysCompany:IMPORT")
    @ApiOperation(position = 10, value = "公司表-导入" , notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("公司表-导入成功" , service.saveBatch(EasyPoiUtil.importExcel(file, SysCompany.class)));
    }

    /**
     * 获取用户数据权限
     */
    @AutoLog(value = "获取用户数据权限")
    @GetMapping("/userDataRule")
    @ApiOperation(position = 10, value = "公司表-获取用户数据权限" , notes = "通过TOKEN获取用户数据权限")
    public R<JSONObject> getUserDataRule() {
        LoginUser currentUser = UserUtil.getCurrentUser();

        List<SysCompany> userCompanyList = userCompanyService.getUserCompanyList(currentUser.getId());
        List<String> current = (List<String>) redisUtil.get(SYS_USERS_COMPANY_CACHE + currentUser.getId());

        //无数据权限，就查看自己的
        if (CollectionUtil.isEmpty(userCompanyList) || current == null) {
            current = new ArrayList<>();
            current.add(currentUser.getTenantId());
            redisUtil.set(SYS_USERS_COMPANY_CACHE + currentUser.getId(), current);
        }

        //拼装结果集
        JSONObject result = new JSONObject();
        result.put("dataRules" , userCompanyList);
        result.put("current" , current);

        return R.ok("获取用户数据权限成功" , result);
    }

    /**
     * 切换用户数据权限
     */
    @AutoLog(value = "切换用户数据权限")
    @PostMapping("/userDataRule")
    @ApiOperation(position = 10, value = "公司表-切换用户数据权限" , notes = "切换当前用户所拥有的数据标识,用[,]分割")
    public R<SysCompany> changeUserDataRule(
            @ApiParam("数据权限ID") @RequestParam(name = "dataRuleIds") String dataRuleIds
    ) {
        LoginUser currentUser = UserUtil.getCurrentUser();
        List<String> current;
        //先检查是否切换成自己的公司
        if (currentUser.getTenantId().equalsIgnoreCase(dataRuleIds)) {
            current = new ArrayList<>();
            current.add(currentUser.getTenantId());
        } else {
            //获取所有数据权限
            List<SysCompany> userCompanyList = userCompanyService.getUserCompanyList(currentUser.getId());
            userCompanyList = userCompanyList.stream().filter(item -> Arrays.asList(dataRuleIds.split(",")).contains(item.getId())).collect(Collectors.toList());
            //无数据权限，就查看自己的
            if (CollectionUtil.isEmpty(userCompanyList)) {
                return R.error("找不到该数据权限！");
            }
            current = userCompanyList.stream().map(SysCompany::getId).collect(Collectors.toList());
            //将自己的公司移动到最后
            current.remove(currentUser.getTenantId());
            current.add(currentUser.getTenantId());
        }
        redisUtil.set(SYS_USERS_COMPANY_CACHE + currentUser.getId(), current);
        return R.ok("切换用户数据权限成功");
    }

}
