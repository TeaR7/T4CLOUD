<template>
  <div class="navBarWrap">
    <div class="headDiv">
      <div class="menuicon-box" @click="handleIsOpen" :style="{'transform': (!isCollapse ? 'rotate(0deg)':'rotate(180deg)')}">
        <i class="el-icons-shousuo"></i>
      </div>
      <div class="headText web">欢迎进入 T4Cloud 企业级快速开发平台</div>
      <div class="headText h5">T4Cloud</div>
      <div>
        <el-dropdown class="nameSpan" @command="handleCommand">
          <span class="el-dropdown-link">
            <img class="userImg" :src="this.backAvatar()"
              alt="avatar" />
            <span class="web">
              {{welcome}}，{{username}}
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="systemSetting">系统设置</el-dropdown-item>
            <el-dropdown-item command="changePassword">修改密码</el-dropdown-item>
            <el-dropdown-item command="loginOut">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <ForgetPassword :isShow="isShowPassWordCom" @hidePasswordCom="handPasswordHide"></ForgetPassword>
    <TSettingDrawer ref="settingDrawer"></TSettingDrawer>
  </div>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
import ForgetPassword from "../../views/user/modals/ForgetPasswordModal";
import TSettingDrawer from "../Setting/TSettingDrawer"

export default {
  data() {
    return {
      isShowPassWordCom: false,
      welcome: "欢迎您",
      isCollapse: {
        type: Boolean,
        default: false
      }
    };
  },
  components: {
    ForgetPassword,
    TSettingDrawer
  },
  computed:{
    ...mapGetters(["username","userInfo"])
  },
  //页面加载时
  created() {
    this.getWelcome();
  },
  methods: {
    ...mapActions(["Logout"]),
    // 问候语句
    getWelcome() {
      var now = new Date(),
        hour = now.getHours();
      if (hour < 6) {
        this.welcome = "凌晨好";
      } else if (hour < 9) {
        this.welcome = "早上好";
      } else if (hour < 12) {
        this.welcome = "上午好";
      } else if (hour < 14) {
        this.welcome = "中午好";
      } else if (hour < 17) {
        this.welcome = "下午好";
      } else if (hour < 19) {
        this.welcome = "傍晚好";
      } else if (hour < 22) {
        this.welcome = "晚上好";
      } else {
        this.welcome = "夜里好";
      }
    },
    // 展开收缩
    handleIsOpen() {
      // console.log(this.username());
      // console.log(this.userInfo());
      // console.log(
      //   this.userInfo().avatar
      //     ? this.userInfo().avatar
      //     : "https://git.t4cloud.com/img/favicon.png"
      // );
      this.$emit("change");
      this.isCollapse = !this.isCollapse;
    },
    // 隐藏修改密码弹框
    handPasswordHide() {
      this.isShowPassWordCom = false;
    },
    // 退出登录
    handleCommand(command) {
      // console.log(command);
      if (command == 'systemSetting') {
        this.$refs.settingDrawer.drawer = true
      } else if (command == 'changePassword') {
        this.isShowPassWordCom = true;
      } else if (command == 'loginOut') {
        this.Logout().then(res => {
          if (res.success) {
            this.$router.push('/user/login');
          }
          this.$message.success(res.message);
        });
      }
    },
    // 获取头像
    backAvatar() {
      let avatar = 'https://git.t4cloud.com/img/favicon.png';
      if (this.userInfo.avatar) {
        if (this.userInfo.avatar.indexOf('http') >= 0) {
          avatar = this.userInfo.avatar
        } else {
          avatar = process.env.VUE_APP_URL + '/T4Cloud-Support/file/view/' + this.userInfo.avatar
        }
      }
      return avatar
    }
  }
};
</script>

<style lang="scss" scoped>
.navBarWrap {
  height: 100%;
  .menuicon-box {
    padding: 10px;
    cursor: pointer;
    &:hover {
      background-color: rgba(197, 197, 197, 0.5);
    }
  }
  .headDiv {
    height: 100%;
    color: #333;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .nameSpan {
      cursor: pointer;
      margin-left: 10px;
      font-size: 15px;
      color: #000;
      &:hover {
        background-color: rgba(197, 197, 197, 0.2);
        color: lightblue;
      }
    }

    .userImg {
      width: 36px;
      border-radius: 18px;
      margin-right: 5px;
    }

    .headText {
      font-weight: 800;
      color: #8c939d;
    }
  }
}

@media screen and (max-width: 650px) {
  .web {
    display: none;
  }
}

@media screen and (min-width: 651px) {
  .h5 {
    display: none;
  }
}
</style>
