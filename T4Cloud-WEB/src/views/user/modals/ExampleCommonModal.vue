<template>
  <div class="fixedDialog">
    <el-drawer :visible.sync="isShow" direction="rtl" :size="device=='mobile'?'100%':'550px'" :before-close="close">
      <div slot="title">
        <h2 class="el-drawer__title">{{title}}</h2>
      </div>
      <div class="dialog__body">
        <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="130px"
          v-loading="loading">
          <el-form-item label="字符串查询" prop="str">
            <el-input placeholder="输入字符串查询" v-model="forms.str" :readonly="disableSubmit"></el-input>
          </el-form-item>
          <el-form-item label="长文本查询" prop="txt">
            <el-input placeholder="输入长文本查询" v-model="forms.txt" type="textarea" :rows="2" :readonly="disableSubmit">
            </el-input>
          </el-form-item>
          <el-form-item label="数字类型查询" prop="num">
            <el-input-number placeholder="输入数字类型查询" v-model="forms.num" :disabled="disableSubmit">
            </el-input-number>
          </el-form-item>
          <el-form-item label="日期查询" prop="queryDate">
            <el-date-picker placeholder="选择日期查询" v-model="forms.queryDate" :readonly="disableSubmit" format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="日期时间查询" prop="queryDateTime">
            <el-date-picker type="datetime" placeholder="选择日期时间查询" v-model="forms.queryDateTime" :readonly="disableSubmit"
              format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-form-item>
          <el-form-item label="时间查询" prop="queryTime">
            <el-time-picker placeholder="选择时间查询" v-model="forms.queryTime" :readonly="disableSubmit" value-format="HH:mm:ss"></el-time-picker>
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
  name: 'ExampleCommonModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
        str: { required: true, message: '字符串查询不允许为空', trigger: 'blur' },
        txt: { required: true, message: '长文本查询不允许为空', trigger: 'blur' },
        num: { required: true, message: '数字类型查询不允许为空', trigger: 'blur' },
      },
      url: {
        detail: '/T4Cloud-User/ExampleCommon/detail',
        save: '/T4Cloud-User/ExampleCommon/save',
        update: '/T4Cloud-User/ExampleCommon/update',
      },
    }
  },
  methods: {
    //此处可填充默认值
    initForm() {
      return {
        id: null,
        str: null, // 字符串查询
        txt: null, // 长文本查询
        num: null, // 数字类型查询
        queryDate: null, // 日期查询
        queryDateTime: null, // 日期时间查询
        queryTime: null, // 时间查询
      }
    },
  }
}
</script>
