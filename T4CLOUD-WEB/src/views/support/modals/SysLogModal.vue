<template>
    <div class="fixedDialog">
        <el-drawer :visible.sync="isShow" direction="rtl" :size="device=='mobile'?'100%':'750px'" :before-close="close"> 
            <div slot="title">
                <h2 class="el-drawer__title">{{title}}</h2>
            </div>
            <div class="dialog__body">
                <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
                    <el-form-item label="日志类型" prop="logType">
                        <el-tag>{{forms.logType_dict}}</el-tag>
                    </el-form-item>
                    <el-form-item label="日志内容" prop="logContent">
                        <el-input v-model="forms.logContent" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="操作类型" prop="operateType">
                        <el-tag :type="forms.operateType == 1 ? 'success' : forms.operateType == 2 ? 'info' :forms.operateType == 3 ? 'warning' :forms.operateType == 4 ? '' : 'danger'">
                            {{forms.operateType_dict}}
                        </el-tag>
                    </el-form-item>
                    <el-form-item label="结果记录" prop="result">
                        <el-input v-model="forms.result" type="textarea" :rows="5" :readonly="disableSubmit">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="执行状态" prop="resultType">
                        <el-input  v-model="forms.resultType_dict" :readonly="disableSubmit">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="操作用户" prop="username">
                        <el-input  v-model="forms.username" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="IP" prop="ip">
                        <el-input  v-model="forms.ip" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="调用方法" prop="method">
                        <el-input v-model="forms.method" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="请求路径" prop="requestUrl">
                        <el-input v-model="forms.requestUrl" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="请求参数" prop="requestParam">
                        <el-input v-model="forms.requestParam" type="textarea" :rows="2" :readonly="disableSubmit">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="请求类型" prop="requestType">
                        <el-input v-model="forms.requestType" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                    <el-form-item label="耗时" prop="costTime">
                        <el-input :value="forms.costTime + 'ms'" :readonly="disableSubmit"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="dialog__footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit">确定</el-button>
            </div>
        </el-drawer>
    </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'
export default {
  name: 'SysLogModal',
  mixins: [T4CloudModalMixin],
  data() {
      return {
          url: {
              detail: '/T4Cloud-Support/SysLog/detail',
              save: '/T4Cloud-Support/SysLog/save',
              update: '/T4Cloud-Support/SysLog/update',
          },
      }
  },
  methods: {
      //此处可填充默认值
      initForm() {
          return {
              id: null,
              logType_dict: null, // 日志类型
              logContent: null, // 日志内容
              operateType: null, // 操作类型(1-增，2-删，3-改，4-查)
              operateType_dict: null, // 操作类型(1-增，2-删，3-改，4-查)
              result: null, // 操作结果记录
              resultType_dict: null, // 是否异常（0-异常，1-正常）
              username: null, // 操作用户名称
              ip: null, // IP
              method: null, // 请求java方法
              requestUrl: null, // 请求路径
              requestParam: null, // 请求参数
              requestType: null, // 请求类型
              costTime: null, // 耗时
          }
      },
  }
}
</script>
