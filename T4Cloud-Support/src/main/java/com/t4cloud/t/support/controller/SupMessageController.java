package com.t4cloud.t.support.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.support.entity.SupMessage;
import com.t4cloud.t.support.entity.SupMessageTemplate;
import com.t4cloud.t.support.service.ISupMessageService;
import com.t4cloud.t.support.service.ISupMessageTemplateService;
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

/**
 * 消息列表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-03-31
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "消息列表", tags = "消息列表接口", position = 2)
@RequestMapping("/SupMessage")
public class SupMessageController extends T4Controller<SupMessage, ISupMessageService> {

    @Autowired
    private ISupMessageTemplateService supMessageTemplateService;

    /**
     * 详情
     */
    @AutoLog(value = "消息列表-详情")
    @GetMapping("/detail")
    @RequiresPermissions("support:SupMessage:VIEW")
    @ApiOperation(position = 1, value = "消息列表-详情", notes = "传入supMessage")
    public R<SupMessage> detail(SupMessage supMessage, HttpServletRequest req) {
        QueryWrapper<SupMessage> supMessageQueryWrapper = T4Query.initQuery(supMessage, req.getParameterMap());
        SupMessage detail = service.getOne(supMessageQueryWrapper);
        return R.ok("消息列表-详情查询成功", detail);
    }

    /**
     * 全部列表 消息列表
     */
    @AutoLog(value = "消息列表-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("support:SupMessage:VIEW")
    @ApiOperation(position = 2, value = "消息列表-全部列表", notes = "传入supMessage")
    public R<List<SupMessage>> list(SupMessage supMessage, HttpServletRequest req) {
        QueryWrapper<SupMessage> supMessageQueryWrapper = T4Query.initQuery(supMessage, req.getParameterMap());
        List<SupMessage> list = service.list(supMessageQueryWrapper);
        return R.ok("消息列表-全部列表查询成功", list);
    }

    /**
     * 分页查询 消息列表
     */
    @AutoLog(value = "消息列表-分页查询")
    @GetMapping("/page")
    @RequiresPermissions("support:SupMessage:VIEW")
    @ApiOperation(position = 3, value = "消息列表-分页查询", notes = "传入supMessage")
    public R<IPage<SupMessage>> page(SupMessage supMessage,
                                     @ApiParam(name = "pageNo")
                                     @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                     @ApiParam(name = "pageSize")
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {
        QueryWrapper<SupMessage> supMessageQueryWrapper = T4Query.initQuery(supMessage, req.getParameterMap());
        IPage<SupMessage> pages = service.page(new Page<>(pageNo, pageSize), supMessageQueryWrapper);
        return R.ok("消息列表-分页查询查询成功", pages);
    }

    /**
     * 通过模板新增 消息列表
     */
    @AutoLog(value = "消息列表-通过模板新增", operateType = 1)
    @PutMapping("/saveByTemplate")
    @ApiOperation(position = 4, value = "消息列表-新增", notes = "传入supMessage")
    public R saveByTemplate(@RequestBody SupMessage supMessage) {
        service.saveByTemplate(supMessage);
        return R.ok("消息列表-新增成功");
    }

    /**
     * 新增 消息列表
     */
    @AutoLog(value = "消息列表-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("support:SupMessage:ADD")
    @ApiOperation(position = 4, value = "消息列表-新增", notes = "传入supMessage")
    public R save(@Valid @RequestBody SupMessage supMessage, BindingResult bindingResult) {
        //检查是否填充模板
        if (StrUtil.isBlank(supMessage.getContent()) && StrUtil.isNotBlank(supMessage.getMessageTemplateCode())) {
            //获取模板
            SupMessageTemplate supMessageTemplate = supMessageTemplateService.
                    lambdaQuery().eq(SupMessageTemplate::getTemplateCode, supMessage.getMessageTemplateCode()).one();
            if (supMessageTemplate == null) {
                return R.error("找不到该模板");
            }
            //从模板生成内容
            String title = supMessageTemplate.getTemplateName();
            String content = supMessageTemplate.getTemplateContent();
            JSONObject param = JSONUtil.parseObj(supMessage.getParam());
            for (String key : param.keySet()) {
                title = title.replace("${" + key + "}", param.getStr("key"));
                content = content.replace("${" + key + "}", param.getStr("key"));
            }

            //从模板确定类型
            supMessage.setMessageType(supMessageTemplate.getTemplateType());
            supMessage.setTitle(title);
            supMessage.setContent(content);
        }

        //拆分多个收件人
        String[] targets = supMessage.getTarget().split(StrUtil.COMMA);
        List<String> targetErrList = new ArrayList<>();
        for (String target : targets) {
            try {
                SupMessage message = new SupMessage();
                BeanUtil.copyProperties(supMessage, message);
                /** TODO 此处应该针对当前消息类别，验证接收人的格式，比如邮件地址，电话号码等等
                 *
                 * -by TeaR  -2020/4/1-12:43
                 */
                message.setTarget(target);
                if (ObjectUtil.isEmpty(message.getSendTime())) {
                    //如果没有填写，就填充当前时间，即刻发送
                    message.setSendTime(DateUtil.date());
                }
                service.save(message);
            } catch (Exception e) {
                //catch避免一个异常影响其他
                targetErrList.add(target);
                continue;
            }
        }

        if (CollectionUtil.isEmpty(targetErrList)) {
            return R.ok("消息列表-全部新增成功");
        } else {
            return R.error("消息列表-以下未成功:" + JSONUtil.toJsonStr(targetErrList));
        }

    }

    /**
     * 修改 消息列表
     */
    @AutoLog(value = "消息列表-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"support:SupMessage:ADD", "support:SupMessage:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "消息列表-修改", notes = "传入supMessage")
    public R update(@RequestBody SupMessage supMessage) {
        return R.ok("消息列表-修改成功", service.updateById(supMessage));
    }


    /**
     * 删除 消息列表
     */
    @AutoLog(value = "消息列表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("support:SupMessage:DELETE")
    @ApiOperation(position = 8, value = "消息列表-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam(name = "ids") String ids) {
        return R.ok("消息列表-删除成功", service.removeByIds(Arrays.asList(ids.split(","))));

    }


    /**
     * 导出 消息列表
     */
    @AutoLog(value = "消息列表-导出")
    @GetMapping("/export")
    @RequiresPermissions("support:SupMessage:EXPORT")
    @ApiOperation(position = 9, value = "消息列表-导出", notes = "传入查询条件，选中的ID（多个用,分割），以及自定义的列（多个用,分割）")
    public void export(SupMessage supMessage,
                       @ApiParam("选中导出的数据") @RequestParam(name = "selectedRowKeys", required = false) String selectedRowKeys,
                       @ApiParam("自定义导出列") @RequestParam(name = "selectedColKeys", required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SupMessage> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(supMessage, req.getParameterMap()) : new QueryWrapper<SupMessage>().in("id", selectedRowKeys.split(","));
        super.exportExl("消息列表", selectedColKeys, queryWrapper);
    }

    /**
     * 导入 消息列表
     */
    @AutoLog(value = "消息列表-导入", operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("support:SupMessage:IMPORT")
    @ApiOperation(position = 10, value = "消息列表-导入", notes = "传入填好的模板文件即可")
    public R importXls(@ApiParam("EXCEL文件") MultipartFile file) {
        return R.ok("消息列表-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, SupMessage.class)));
    }

}
