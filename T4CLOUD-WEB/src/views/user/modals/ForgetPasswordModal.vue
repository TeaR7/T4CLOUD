<template>
  <div class="forgetPasswordWrap">
    <el-dialog title="修改密码" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="dataFrom" class="dialogForm" :rules="rules" ref="baseForm" label-width="100px">
        <el-form-item label="密码" prop="password">
          <el-input v-model="dataFrom.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="重复密码" prop="newPassword">
          <el-input v-model="dataFrom.newPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-col :span="12">
            <el-input v-model="dataFrom.code"></el-input>
          </el-col>
          <el-col :span="10" :offset="1">
            <div class="imgAutoDiv">
              <t-graphic-code class="graphicCode" @success="generateCode" ref="jgraphicCodeRef" remote :contentHeight="36"></t-graphic-code>
            </div>
          </el-col>
        </el-form-item>
        <el-form-item style="text-align:right;">
          <el-button @click="resetForm('baseForm')">取消</el-button>
          <el-button type="primary" @click="submitForm('baseForm')">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { fetchChangePwd } from '../../../apis/login'
import TGraphicCode from "@/components/T4Cloud/TGraphicCode";
import { mapGetters } from "vuex";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value == null || value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length < 6) {
        callback(new Error('密码长度不少于6个字符'));
      } else {
        if (this.dataFrom.newPassword !== '') {
          this.$refs.baseForm.validateField('newPassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value == null || value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value.length < 6) {
        callback(new Error('密码长度不少于6个字符'));
      } else if (value !== this.dataFrom.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if (value.toLowerCase() != this.verifiedCode) {
        console.log(value, this.verifiedCode)
        callback(new Error('验证码不正确'));
      } else {
        callback();
      }
    };
    return {
      dataFrom: {
        password: '',
        newPassword: '',
        code: ''
      },
      verifiedKey: '', // 验证码key
      verifiedCode: '', // 验证码code
      rules: {
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        newPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        code: [
          { required: true, validator: validateCode, trigger: 'blur' }
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
  components: {
    TGraphicCode
  },
  computed: {
    ...mapGetters([
      'device'
    ])
  },
  methods: {
    close() {
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
      // if (this.dataFrom.code.toLowerCase() !== this.verifiedCode) {
      //   this.$message.warning('请输入正确的验证码!')
      //   return
      // }
      const data = {
        password: this.dataFrom.password,
        code: this.verifiedCode,
        codeKey: this.verifiedKey
      }
      fetchChangePwd(data).then(res => {
        if (res.code == 200) {
          this.$message.success(res.message)
          this.$emit('hidePasswordCom')
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 获取验证码
    generateCode(value, key) {
      // this.dataFrom.code = value.toLowerCase();
      this.verifiedKey = key;
      this.verifiedCode = value.toLowerCase();
    }
  }
}
</script>

