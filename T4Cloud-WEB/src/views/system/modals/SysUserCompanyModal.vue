<template>
  <div>
      <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
          <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
            <el-form-item label="用户id" prop="userId">
                <el-input placeholder="输入用户id" v-model="forms.userId" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="公司id" prop="companyId">
                <el-input placeholder="输入公司id" v-model="forms.companyId" :readonly="disableSubmit"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['system:SysUserCompany:ADD','system:SysUserCompany:EDIT']">确定</el-button>
          </span>
      </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name:'SysUserCompanyModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
      },
      url: {
          detail: '/T4Cloud-System/SysUserCompany/detail',
          save: '/T4Cloud-System/SysUserCompany/save',
          update: '/T4Cloud-System/SysUserCompany/update',
      }
    }
  },
  methods: {
      //初始化form表单字段
      initForm() {
          return {
              id: null,
              userId: null, // 用户id
              companyId: null, // 公司id
          }
      },
  }
}
</script>
