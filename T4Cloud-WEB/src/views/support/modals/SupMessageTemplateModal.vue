<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="CODE" prop="templateCode">
          <el-input placeholder="输入模板CODE" v-model="forms.templateCode" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="templateName">
          <el-input placeholder="输入模板标题" v-model="forms.templateName" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="templateType">
          <t-dict v-model="forms.templateType" dictCode="msg_template_type" :readonly="disableSubmit"></t-dict>
        </el-form-item>
        <el-form-item label="模板内容" prop="templateContent">
          <el-input placeholder="输入模板内容" v-model="forms.templateContent" type="textarea" :rows="2" :readonly="disableSubmit">
          </el-input>
          <div class="tips">变量用${}来包裹，例如${username}</div>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['support:SupMessageTemplate:ADD','support:SupMessageTemplate:EDIT']">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'


export default {
  name: 'SupMessageTemplateModal',
  mixins: [T4CloudModalMixin],
  data() {
    const valiteCode = this.valiteValue('模版CODE');
    return {
      rules: {
        templateCode: { required: true, validator: valiteCode, trigger: 'blur' },
        templateName: { required: true, message: '模版标题不允许为空', trigger: 'blur' },
        templateType: { required: true, message: '模板类型：1短信 2邮件 3微信不允许为空', trigger: 'blur' },
        templateContent: { required: true, message: '模版内容不允许为空', trigger: 'blur' }
      },
      url: {
        save: '/T4Cloud-Support/SupMessageTemplate/save',
        update: '/T4Cloud-Support/SupMessageTemplate/update',
        check: '/T4Cloud-Support/SupMessageTemplate/check'
      }
    }
  },
  methods: {
    //初始化form表单字段
    initForm() {
      return {
        id: null,
        templateCode: null, // 模板CODE
        templateName: null, // 模板标题
        templateType: null, // 模板类型：1短信 2邮件 3微信
        templateContent: null, // 
      }
    },
  }
}
</script>
