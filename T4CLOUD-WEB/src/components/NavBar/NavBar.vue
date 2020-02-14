<template>
  <div class="navBarWrap">
    <!-- <el-header> -->
    <div class="headDiv">
      <div>
        <i class="el-icons-shousuo" @click="handleIsOpen"></i>
      </div>
      <div>
        <img class="userImg" src="../../assets/icon_user.png" alt="">
        <el-dropdown class="nameSpan" @command="handleCommand">
          <span class="el-dropdown-link">
            {{username()}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="changePassword">修改密码</el-dropdown-item>
            <el-dropdown-item command="loginOut">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <ForgetPassword :isShow="isShowPassWordCom"></ForgetPassword>
    <!-- </el-header> -->
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
import ForgetPassword from "../ForgetPassword/ForgetPassword";
export default {
  data() {
    return {
       isShowPassWordCom: false
    }
  },
  components: {
    ForgetPassword
  },
  methods: {
     ...mapGetters(["username"]),
     ...mapActions(['Logout']),
    // 展开收缩
    handleIsOpen() {
      console.log(this.username())
      this.$emit('change')
    },
    // 退出登录
    handleCommand(command) {
      console.log(command)
      if (command == 'changePassword') {
        this.isShowPassWordCom = true
      } else if (command == 'loginOut') {
        this.Logout().then(res=>{
          if (res.code == 200) {
            this.$router.push('/')
          }
          this.$message(res.message);
        })
        // this.$router.push('/')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.navBarWrap {
  height: 100%;
  .headDiv {
    height: 100%;
    color: #333;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .userImg {
      width: 36px;
      border-radius: 18px;
    }
    .nameSpan {
      margin-left: 10px;
      font-size: 15px;
      color: #000;
    }
  }
}
</style>
