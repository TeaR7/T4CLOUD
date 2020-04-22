<template>
  <div>
      <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
          <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
            <el-form-item label="流程定义主键" prop="processDefinitionId">
                <el-input placeholder="输入流程定义主键" v-model="forms.processDefinitionId" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="流程实例主键" prop="processInstanceId">
                <el-input placeholder="输入流程实例主键" v-model="forms.processInstanceId" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="用户ID" prop="userId">
                <el-input placeholder="输入用户ID" v-model="forms.userId" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="用户姓名" prop="userNickname">
                <el-input placeholder="输入用户姓名" v-model="forms.userNickname" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="开始时间" prop="startTime">
                <el-date-picker type="datetime" placeholder="选择开始时间" v-model="forms.startTime" :readonly="disableSubmit"
                        format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
                <el-date-picker type="datetime" placeholder="选择结束时间" v-model="forms.endTime" :readonly="disableSubmit"
                        format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="请假理由" prop="reason">
                <el-input placeholder="输入请假理由" v-model="forms.reason" :readonly="disableSubmit"></el-input>
            </el-form-item>
            <el-form-item label="申请时间" prop="applyTime">
                <el-date-picker type="datetime" placeholder="选择申请时间" v-model="forms.applyTime" :readonly="disableSubmit"
                        format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
          </el-form>
          <span slot="footer">
                <el-button @click="close">取消</el-button>
                <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit" v-auth="['user:ProcessLeave:ADD','user:ProcessLeave:EDIT']">确定</el-button>
          </span>
      </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name:'ProcessLeaveModal',
  mixins: [T4CloudModalMixin],
  data() {
    return {
      rules: {
      },
      url: {
          detail: '/T4Cloud-User/ProcessLeave/detail',
          save: '/T4Cloud-User/ProcessLeave/save',
          update: '/T4Cloud-User/ProcessLeave/update',
      }
    }
  },
  methods: {
      //初始化form表单字段
      initForm() {
          return {
              id: null,
              processDefinitionId: null, // 流程定义主键
              processInstanceId: null, // 流程实例主键
              userId: null, // 用户ID
              userNickname: null, // 用户姓名
              startTime: null, // 开始时间
              endTime: null, // 结束时间
              reason: null, // 请假理由
              applyTime: null, // 申请时间
          }
      },
  }
}
</script>
