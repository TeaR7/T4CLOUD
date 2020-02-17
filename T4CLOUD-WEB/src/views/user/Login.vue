<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <img src="@/assets/logos/logo-o-w.png" alt="logo" />
        <div class="title">T4CLOUD</div>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <i :class="passwordType === 'password' ? 'el-icons-yanjing' : 'el-icons-yanjing-'"></i>
        </span>
      </el-form-item>
      <el-form-item prop="code">
        <el-col :span="14">
          <span class="svg-container">
            <i class="el-icon-chat-dot-round"></i>
          </span>
          <el-input name="code" ref="code" v-model="loginForm.code" placeholder="验证码" />
        </el-col>

        <el-col :span="10">
          <div class="imgAutoDiv">
            <t-graphic-code
              class="graphicCode"
              @success="generateCode"
              ref="jgraphicCodeRef"
              remote
              :contentHeight="contentHeight"
            ></t-graphic-code>
          </div>
        </el-col>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-top:10px;margin-bottom:30px;"
        @click.native.prevent="handleLogin"
      >{{$t('login.login')}}</el-button>

      <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span>password: any</span>
      </div>
    </el-form>
    <el-footer class="main-footer">
        Copyright &copy; 2019-2024 Power by
        <a href="https://t4cloud.com/" target="_blank" style="color: gray">T4Cloud</a>
    </el-footer>
  </div>
</template>

<script>
import TGraphicCode from "@/components/T4Cloud/TGraphicCode";
import { mapActions } from "vuex";
// import store from '@/store/'
// import { fetchLogin } from '@/apis/login'
// import { validUsername } from "@/utils/validate";
export default {
  name: "Login",
  data() {
    const validateUsername = (rule, value, callback) => {
      // if (!validUsername(value)) {
      if (value.length <= 0) {
        callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value.length < 4) {
        callback(new Error("密码不能少于4位"));
      } else {
        callback();
      }
    };
    const validateCode = (rule, value, callback) => {
      if (value.length <= 0) {
        callback(new Error("请输入验证码"));
      } else if (value.toLowerCase() != this.verifiedCode) {
        callback(new Error("验证码错误"));
      } else {
        callback();
      }
    };
    return {
      contentHeight: 44,
      verifiedKey: "",
      loginForm: {
        username: "admin",
        password: "123456",
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
    TGraphicCode
  },
  watch: {
    // $route: {
    //   handler: function (route) {
    //     this.redirect = route.query && route.query.redirect
    //   },
    //   immediate: true
    // }
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
          // this.loading = true
          // this.$store.dispatch('user/login', this.loginForm).then(() => {
          //   this.$router.push({ path: this.redirect || '/' })
          //   this.loading = false
          // }).catch(() => {
          //   this.loading = false
          // })
          const data = {
            code: this.loginForm.code,
            codeKey: this.verifiedKey,
            password: this.loginForm.password,
            username: this.loginForm.username
          };
          this.Login(data)
            .then(res => {
              if (res.code == 200) {
                this.$router.push({
                  path: "/firstpage"
                });
              }
              this.$message(res.message);
            })
            .catch(error => {
              this.$message.error(error.message);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    generateCode(value, key) {
      this.loginForm.code = value.toLowerCase();
      this.verifiedKey = key;
      this.verifiedCode = value.toLowerCase();
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
    .imgAutoDiv {
      margin-top: 4px;
      height: 44px;
      width: 100%;
      // background-color: red;
      .graphicCode {
        float: right;
        height: 100%;
      }
    }
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 130px 35px 0;
    margin: 0 auto;
    overflow: hidden;
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
    top:-20px;
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
  .main-footer {
    line-height: 60px;
    width: 100%;
    position: absolute;
    bottom: 0;
    text-align: center;
    color: #ccc;
    font-size: 12px;
  }
}
</style>
