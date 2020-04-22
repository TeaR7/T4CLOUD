<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
      label-position="left">
      <div class="title-container">
        <img src="@/assets/logos/logo-o-w.png" alt="logo" />
        <div class="title">T4Cloud</div>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input ref="username" v-model="loginForm.username" placeholder="用户名" name="username" type="text" tabindex="1" @blur.prevent="changeBlur"
          auto-complete="on" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="密码" @blur.prevent="changeBlur"
          name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <i :class="passwordType === 'password' ? 'el-icons-yanjing' : 'el-icons-yanjing-'"></i>
        </span>
      </el-form-item>
      <el-form-item prop="code">
        <el-col :span="14">
          <span class="svg-container">
            <i class="el-icons-yanzhengma" style="font-size:14px"></i>
          </span>
          <el-input name="code" ref="code" v-model="loginForm.code" placeholder="验证码" @change="handleLogin" @blur.prevent="changeBlur"/>
        </el-col>

        <el-col :span="10">
          <div class="imgAutoDiv">
            <t-graphic-code class="graphicCode" @success="generateCode" ref="jgraphicCodeRef" remote :contentHeight="contentHeight"></t-graphic-code>
            <div style="opacity: 0;width: 0;">t</div>
          </div>
        </el-col>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-top:10px;margin-bottom:30px;height:44px;"
        @click.native.prevent="handleLogin">{{$t('login.login')}}
      </el-button>

      <!-- <div class="tips">
              <span style="margin-right:20px;">username: admin</span>
              <span>password: any</span>
            </div> -->
    </el-form>
    <t-footer :footerType="1"></t-footer>
  </div>
</template>

<script>
// import TGraphicCode from "@/components/T4Cloud/TGraphicCode";
import Vue from 'vue'
import { SYS_LOGIN_BACK_PATH } from "@/store/mutation-types";
import { mapActions } from "vuex";
// import TFooter from "@/components/T4Cloud/TFooter"

const verifiedCode = process.env.VUE_APP_VERIFIED_CODE === 'true';

export default {
  name: "Login",
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value == null || value.length <= 0) {
        callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value == null || value.length < 6) {
        callback(new Error("密码不能少于6位"));
      } else {
        callback();
      }
    };
    const validateCode = (rule, value, callback) => {
      if (value == null || value.length <= 0) {
        callback(new Error("请输入验证码"));
      } else if (value.toLowerCase() != this.verifiedCode) {
        callback(new Error("验证码错误"));
      } else {
        callback();
      }
    };
    return {
      contentHeight: 47,
      verifiedKey: "",
      loginForm: {
        username: "",
        password: "",
        code: ""
      },
      verifiedCode: "",
      loginRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername }
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword }
        ],
        code: [{ required: true, trigger: "blur", validator: validateCode }]
      },
      loading: false,
      passwordType: "password",
      redirect: undefined
    };
  },
  components: {
    // TGraphicCode
    // TFooter
  },
  methods: {
    ...mapActions(["Login"]),
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          const data = {
            code: this.loginForm.code,
            codeKey: this.verifiedKey,
            password: this.loginForm.password,
            username: this.loginForm.username
          };
          this.Login(data).then(res => {
            if (res.success) {
              var path = '/dashboard/analysis'
              if (Vue.ls.get(SYS_LOGIN_BACK_PATH)) {
                path = Vue.ls.get(SYS_LOGIN_BACK_PATH)
                Vue.ls.remove(SYS_LOGIN_BACK_PATH)
              }
              this.$router.push({
                path: path
              });
            }
            setTimeout(() => {
              this.loading = false
            }, 100);
            this.$message.success(res.message);

          })
            .catch(error => {
              setTimeout(() => {
                this.loading = false
              }, 100);
               this.$message.error(error.message);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    generateCode(value, key) {
      //是否自动填充
      if (verifiedCode) {
        this.loginForm.code = value.toLowerCase();
        this.loginForm.username = "admin";
        this.loginForm.password = "123654789";
      }
      this.verifiedKey = key;
      this.verifiedCode = value.toLowerCase();
    },
    changeBlur(){
      let u = navigator.userAgent
      // let app = navigator.appVersion;
      let isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
      if(isIOS){
        setTimeout(() => {
          const scrollHeight = document.documentElement.scrollTop || document.body.scrollTop || 0
          window.scrollTo(0, Math.max(scrollHeight - 1, 0))
          }, 50)
      }
    }
  }
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 75%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
$footHeight: 50px;
.login-container {
  // height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  min-height: 100%;
  .login-form {
    // height: calc(100% - #{$footHeight});
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 130px 35px 0;
    margin: 0 auto;
    overflow: hidden;
    flex-grow: 1;

    .imgAutoDiv {
      margin-top: 2px;
      height: 47px;
      width: 100%;
      cursor: pointer;

      .graphicCode {
        float: right;
        height: 100%;
      }
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;
      margin-bottom: 20px;
      top: -20px;
      display: flex;
      align-items: center;
      justify-content: center;

      img {
        width: 60px;
      }

      .title {
        font-size: 33px;
        color: $light_gray;
        font-weight: bold;
        padding-left: 10px;
        display: inline-block;
        line-height: 70px;
        height: 60px;
      }

      .logo {
        display: block;
        height: 40px;
        margin: 0px auto 40px auto;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
}

@media screen and (max-width: 650px) {
  .login-container {
    .login-form {
      padding-top: 100px;
    }
  }
}
</style>
