<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="角色名称" prop="roleName">
          <el-input placeholder="输入角色名称" v-model="forms.roleName" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input placeholder="输入角色编码" v-model="forms.roleCode" :readonly="disableSubmit||forms.id ? true: false"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input placeholder="输入描述" v-model="forms.description" type="textarea" :rows="2" :readonly="disableSubmit">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['system:SysRole:ADD','system:SysRole:EDIT']">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name: 'SysRoleModal',
  mixins: [T4CloudModalMixin],
  data() {
    var validateCheck = this.valiteValue('角色编码');
    return {
      rules: {
        roleName: { required: true, message: '角色名称不允许为空', trigger: 'blur' },
        roleCode: { required: true, validator: validateCheck, trigger: 'blur' }
      },
      url: {
        save: '/T4Cloud-System/SysRole/save',
        update: '/T4Cloud-System/SysRole/update',
        check: '/T4Cloud-System/SysRole/check'
      }
    }
  },
  methods: {
    //初始化form表单字段
    initForm() {
      return {
        id: null,
        roleName: null, // 角色名称
        roleCode: null, // 角色编码
        description: null, // 描述
      }
    },
  }
}
</script>
