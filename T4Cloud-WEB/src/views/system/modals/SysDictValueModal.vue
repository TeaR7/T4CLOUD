<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="字典id" prop="dictId" hidden>
          <el-input placeholder="输入字典id" v-model="forms.dictId" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="字典" prop="dictId">
          <el-input placeholder="" v-model="relationData.name" :readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="text">
          <el-input placeholder="输入字典详情名称" v-model="forms.text" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="数据值" prop="value">
          <el-input-number placeholder="输入字典详情数据值" v-model="forms.value" :disabled="disableSubmit">
          </el-input-number>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" placeholder="输入描述" v-model="forms.description" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="position">
          <el-input-number placeholder="输入排序" v-model="forms.position" :disabled="disableSubmit">
          </el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <t-dict v-model="forms.status" type="radio" dictCode="common_status" :readonly="disableSubmit"></t-dict>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['system:SysDictValue:ADD','system:SysDictValue:EDIT']">确定
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name: 'SysDictValueModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {},
      url: {
        detail: '/T4Cloud-System/SysDictValue/detail',
        save: '/T4Cloud-System/SysDictValue/save',
        update: '/T4Cloud-System/SysDictValue/update',
      }
    }
  },
  props: {
    refresh: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    //初始化form表单字段
    initForm() {
      return {
        id: null,
        dictId: null, // 字典id
        text: null, // 字典项文本
        value: null, // 字典项值
        description: null, // 描述
        position: 10, // 排序
        status: 1, // 状态（1启用 0不启用）
      }
    },
  }
}
</script>
