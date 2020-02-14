<template>
  <el-container style="height: 100%;">
    <SideBar style="height:100%" :isCollapse="isCollapse"></SideBar>
    <el-container>
      <el-header>   
        <NavBar @change="handleIsOpen"></NavBar>
      </el-header>
      <el-main style="padding: 0px;">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideBar from "../components/SideBar/SideBar";
import NavBar from "../components/NavBar/NavBar";
export default {
  data() {
    return {
      isCollapse: false
    }
  },
  components: {
    SideBar,
    NavBar
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
