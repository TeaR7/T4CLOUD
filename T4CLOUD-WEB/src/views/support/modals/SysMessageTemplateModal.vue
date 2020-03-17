<template>
  <div>
      <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
          <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
            <el-form-item label="模板CODE" prop="templateCode">
                <el-input placeholder="输入模板CODE" v-model="forms.templateCode" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="模板标题" prop="templateName">
                <el-input placeholder="输入模板标题" v-model="forms.templateName" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="模板类型：1短信 2邮件 3微信" prop="templateType">
                <el-input placeholder="输入模板类型：1短信 2邮件 3微信" v-model="forms.templateType" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="" prop="templateContent">
                <el-input placeholder="输入" v-model="forms.templateContent" type="textarea" :rows="2" :readonly="disableSubmit">
                </el-input>
            </el-form-item>
            <el-form-item style="text-align:right;">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['Support:SysMessageTemplate:ADD','Support:SysMessageTemplate:EDIT']">确定</el-button>
            </el-form-item>
          </el-form>
      </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name:'SysMessageTemplateModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
        templateCode: { required: true, message: '模板CODE不允许为空', trigger: 'blur' },
        templateType: { required: true, message: '模板类型：1短信 2邮件 3微信不允许为空', trigger: 'blur' },
      },
      url: {
          save: '/T4Cloud-Support/SysMessageTemplate/save',
          update: '/T4Cloud-Support/SysMessageTemplate/update',
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
