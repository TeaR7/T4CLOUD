<template>
  <el-container style="height: 100%;">
    <TSideBar style="height:100%" :isCollapse="isCollapse"></TSideBar>
    <el-container>
      <el-header>   
        <TNavBar @change="handleIsOpen"></TNavBar>
      </el-header>
      <el-main style="padding: 0px;">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import TSideBar from "../components/Layout/TSideBar";
import TNavBar from "../components/Layout/TNavBar";
export default {
  data() {
    return {
      isCollapse: false
    }
  },
  components: {
    TSideBar,
    TNavBar
  },
  methods: {
    // 展开收缩
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
</style>
