<template>
  <el-container style="height: 100%;">
    <TSideBar :isCollapse="isCollapse" @hideSideBar="handleIsOpen"></TSideBar>
    <el-container>
      <el-header>
        <TNavBar @change="handleIsOpen"></TNavBar>
      </el-header>
      <el-main style="padding: 0px">
        <TTabLayout class="web"></TTabLayout>
        <div style="margin:12px;">
          <router-view></router-view>
        </div>
      </el-main>
      <el-footer>
          <TFooterBar :footerType="0"></TFooterBar>
      </el-footer>

    </el-container>
  </el-container>
</template>

<script>
import TSideBar from "../components/Layout/TSideBar";
import TNavBar from "../components/Layout/TNavBar";
import TTabLayout from "../components/Layout/TTabLayout"
import TFooterBar from "../components/Layout/TFooterBar"
export default {
  data() {
    return {
      isCollapse: false
    }
  },
  components: {
    TSideBar,
    TNavBar,
    TFooterBar,
    TTabLayout
  },
  methods: {
    // 展开收缩 隐藏显示
    handleIsOpen() {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.el-header {
  background-color: #fff;
}
@media screen and (max-width: 650px) {
  .web {
    display: none;
  }
}
</style>
