<template>
  <div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
      label-position="left">
      <div class="title-container">
        <img class="logo" src="../assets/logos/logo-h-m-w.png" alt="">
        <!-- <h3 class="title">Login Form</h3> -->
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user"></i>
        </span>
        <el-input ref="username" v-model="loginForm.username" placeholder="用户名" name="username" type="text" tabindex="1"
          auto-complete="on" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock"></i>
        </span>
        <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="密码"
          name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
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
            <j-graphic-code class="graphicCode" @success="generateCode" ref="jgraphicCodeRef" remote :contentHeight="contentHeight"></j-graphic-code>
          </div>
        </el-col>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-top:10px;margin-bottom:30px;"
        @click.native.prevent="handleLogin">登录</el-button>

      <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span>password: any</span>
      </div>
    </el-form>
  </div>
</template>
<script>
export default {

}
</script>

<style lang="scss" scoped>
</style>
