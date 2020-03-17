<template>
  <div>
      <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
          <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
            <el-form-item label="资源名称" prop="name">
                <el-input placeholder="输入资源名称" v-model="forms.name" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="相对路径" prop="path">
                <el-input placeholder="输入相对路径" v-model="forms.path" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="完整资源路径" prop="url">
                <el-input placeholder="输入完整资源路径" v-model="forms.url" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="桶名" prop="bucket">
                <el-input placeholder="输入桶名" v-model="forms.bucket" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="资源类型" prop="mimeType">
                <el-input placeholder="输入资源类型" v-model="forms.mimeType" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="权限策略（1-公开，2-私有）" prop="policy">
                <el-input-number placeholder="输入权限策略（1-公开，2-私有）" v-model="forms.policy" :disabled="disableSubmit">
                </el-input-number>
            </el-form-item>
            <el-form-item label="失效时间" prop="expireTime">
                <el-input placeholder="输入失效时间" v-model="forms.expireTime" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="总访问次数" prop="count">
                <el-input-number placeholder="输入总访问次数" v-model="forms.count" :disabled="disableSubmit">
                </el-input-number>
            </el-form-item>
            <el-form-item style="text-align:right;">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['support:SysResource:ADD','support:SysResource:EDIT']">确定</el-button>
            </el-form-item>
          </el-form>
      </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name:'SysResourceModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
        name: { required: true, message: '资源名称不允许为空', trigger: 'blur' },
        path: { required: true, message: '相对路径不允许为空', trigger: 'blur' },
        url: { required: true, message: '完整资源路径不允许为空', trigger: 'blur' },
        policy: { required: true, message: '权限策略（1-公开，2-私有）不允许为空', trigger: 'blur' },
        count: { required: true, message: '总访问次数不允许为空', trigger: 'blur' },
      },
      url: {
          save: '/T4Cloud-Support/SysResource/save',
          update: '/T4Cloud-Support/SysResource/update',
      }
    }
  },
  methods: {
      //初始化form表单字段
      initForm() {
          return {
              id: null,
              name: null, // 资源名称
              path: null, // 相对路径
              url: null, // 完整资源路径
              bucket: null, // 桶名
              mimeType: null, // 资源类型
              policy: null, // 权限策略（1-公开，2-私有）
              expireTime: null, // 失效时间
              count: null, // 总访问次数
          }
      },
  }
}
</script>
