<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="字典名称" prop="name">
          <el-input placeholder="输入字典名称" v-model="forms.name" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="字典编码" prop="code">
          <el-input placeholder="输入字典编码" v-model="forms.code" :readonly="disableSubmit||forms.id ? true:false"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" placeholder="输入描述" v-model="forms.description" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <t-dict v-model="forms.status" type="radio" dictCode="common_status" :readonly="disableSubmit"></t-dict>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['system:SysDict:ADD','system:SysDict:EDIT']">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'
export default {
  name: 'SysDictModal',
  mixins: [T4CloudModalMixin],
  data() {
    var validateCode = this.valiteValue('字典编码');
    return {
      rules: {
        name: { required: true, message: '字典名称不允许为空', trigger: 'blur' },
        code: { required: true, validator: validateCode, trigger: 'blur' },
        //{ required: true, message: '字典编码不允许为空', trigger: 'blur' },
        status: { required: true, message: '删除状态不允许为空', trigger: 'blur' },
      },
      url: {
        detail: '/T4Cloud-System/SysDict/detail',
        save: '/T4Cloud-System/SysDict/save',
        update: '/T4Cloud-System/SysDict/update',
        check: '/T4Cloud-System/SysDict/check'
      }
    }
  },
  methods: {
    //初始化form表单字段
    initForm() {
      return {
        id: null,
        name: null, // 字典名称
        code: null, // 字典编码
        description: null, // 描述
        status: 1, // 删除状态
      }
    }
  }
}
</script>
