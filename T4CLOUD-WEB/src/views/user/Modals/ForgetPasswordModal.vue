<template>
  <div class="forgetPasswordWrap">
    <el-dialog title="修改密码" :visible.sync="isShow" :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="密码" prop="password">
          <el-input v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item label="重复密码" prop="newPassword">
          <el-input v-model="ruleForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item style="text-align:right;">
          <el-button @click="resetForm('ruleForm')">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { fetchChangePwd } from '../../../apis/login'
export default {
  data() {
    return {
      ruleForm: {
        password: '',
        newPassword: ''
      },
      rules: {
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入重复新密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleClose(){
      this.$emit('hidePasswordCom')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getChangePwd()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.$emit('hidePasswordCom')
    },
    // 修改密码
    getChangePwd() {
      const data = {
        password: this.ruleForm.password
      }
      fetchChangePwd(data).then(res => {
        if(res.code==200){
          this.$message.success(res.message)
          this.$emit('hidePasswordCom')
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.forgetPasswordWrap {
}
</style>
