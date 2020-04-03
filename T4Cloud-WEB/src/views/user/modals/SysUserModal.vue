<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'">
      <el-form :model="forms" :rules="rules" class="dialogForm" ref="baseForm" label-width="100px" v-loading="loading">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="forms.username" :readonly="disableSubmit||forms.id ? true: false"></el-input>
        </el-form-item>
        <el-form-item v-if="!forms.id" label="登录密码" prop="password">
          <el-input v-model="forms.password" :readonly="disableSubmit" show-password></el-input>
        </el-form-item>
        <el-form-item v-if="!forms.id" label="确认密码" prop="rePassword">
          <el-input v-model="forms.rePassword" :readonly="disableSubmit" show-password></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realname">
          <el-input v-model="forms.realname" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="工号" prop="workNo">
          <el-input v-model="forms.workNo" :readonly="disableSubmit||forms.id ? true: false"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择生日" v-model="forms.birthday" format="yyyy-MM-dd" value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;" :disabled="disableSubmit"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别">
          <t-dict v-model="forms.gender" type="radio" dictCode="gender" :readonly="disableSubmit"></t-dict>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="forms.idCard" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="forms.email" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="forms.phone" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="forms.address" :readonly="disableSubmit"></el-input>
        </el-form-item>
        <el-form-item label="用户头像">
          <TPicUpload v-model="forms.avatar" :readonly="disableSubmit" :tips="'推荐图片大小150*150'"></TPicUpload>
        </el-form-item>
        <el-form-item label="状态">
          <t-dict v-model="forms.status" type="radio" dictCode="common_status" :readonly="disableSubmit"></t-dict>
        </el-form-item>
        <el-form-item style="text-align:right;">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="submitForm('baseForm')" :disabled="disableSubmit">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'
import TPicUpload from '@/components/T4Cloud/TPicUpload'

export default {
  name: 'SysUserModal',
  mixins: [T4CloudModalMixin],
  components: {
    TPicUpload
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value == null || value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length < 6) {
        callback(new Error('密码长度不少于6个字符'));
      } else {
        if (this.forms.rePassword !== '') {
          this.$refs.baseForm.validateField('rePassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value == null || value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value.length < 6) {
        callback(new Error('密码长度不少于6个字符'));
      } else if (value !== this.forms.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      const key = rule.field
      const keyStr = key == 'username' ? '用户账号' : '工号'
      if (value == null || value === '') {
        callback(new Error('请输入' + keyStr));
      } else {
        if (!this.forms.id) {
          this.ckeckFieldInfo(key, value).then(res => {
            if (res == false) {
              callback(new Error('已存在该' + keyStr));
            } else {
              callback();
            }
          })
        } else {
          callback();
        }
      }
    };
    return {
      forms: {},
      rules: {
        username: { required: true, validator: validateName, trigger: 'blur' },
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
          // { required: true, message: '请输入密码', trigger: 'blur' },
          // { min: 6, message: '密码长度不少于6个字符', trigger: 'blur' }
        ],
        rePassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        realname: { required: true, message: '请输入真实姓名', trigger: 'blur' },
        workNo: { required: true, validator: validateName, trigger: 'blur' }
      },
      url: {
        detail: '/T4Cloud-User/SysUser/detail',
        save: '/T4Cloud-User/SysUser/save',
        update: '/T4Cloud-User/SysUser/update',
        check: '/T4Cloud-User/SysUser/check'
      },
    }
  },
  methods: {
    //此处可填充默认值
    initForm() {
      return {
        username: null,
        password: null,
        rePassword: null,
        realname: null,
        workNo: null,
        birthday: null,
        gender: null, // 性别(0-默认未知,1-男,2-女)
        email: null,
        phone: null,
        address: null,
        avatar: null,
        id: null,
        idCard: null,
        post: null, // 关联职务表
        status: 1
      }
    },
    //save预处理
    beforeSave() {
      //去除rePassword字段不传给后端
      delete this.forms.rePassword
      return true
      // this.forms.rePassword = null
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
