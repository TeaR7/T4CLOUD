<template>
  <div class="sideBarWrap">
    <div class="headImgDiv">
      <div class="headImgBox">
        <img src="@/assets/logos/logo-o-w.png" alt="logo" />
        <div class="text" :class="!isCollapse?'open':'close'">T4CLOUD</div>
      </div>
    </div>
    <el-menu
      class="el-menu-vertical-demo"
      :default-active="$route.path"
      :default-openeds="['1']"
      background-color="#1d2128"
      text-color="#fff"
      active-text-color="#ffd04b"
      :collapse="isCollapse"
      :collapse-transition="true"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">导航一</span>
        </template>
        <el-menu-item index="/orderManage">订单管理</el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-search"></i>
          <span slot="title">导航二</span>
        </template>
        <el-menu-item index="/test">订单管理</el-menu-item>
        <el-menu-item index="/test">订单管理</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>
<script>
export default {
  name: "SideBar",
  props: {
    isCollapse: {
      type: Boolean,
      default: false
    }
  }
};
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  height: 100%;
}
.el-menu {
  border-right: none !important;
}
</style>
<style lang="scss" scoped>
.sideBarWrap {
  background-color: #1d2128;
  .headImgDiv {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 115px;
    .headImgBox {
      display: flex;
      align-items: center;
    }
    .text {
      color: white;
      font-size: 24px;
      display: flex;
      padding-left: 10px;
      height: 35px;
      line-height: 50px;
    }
    img {
      width: 50px;
      margin-left: 10px;
    }
  }
  .open {
    width: 130px;
    transition: width 0.3s;
    overflow: hidden;
  }
  .close {
    width: 10px;
    transition: width 0.6s;
    overflow: hidden;
  }
}
</style>



