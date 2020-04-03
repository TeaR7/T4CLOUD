<template>
  <el-container style="height: 100%;">
    <!-- <TSideBar :isCollapse="isCollapse" @hideSideBar="handleIsOpen"></TSideBar> -->
    <t-sidebar :isCollapse="isCollapse" @hideSideBar="handleIsOpen" :logo="require('@/assets/logos/logo-o-w.png')"></t-sidebar>
    <el-container>
      <el-header>
        <TNavBar @change="handleIsOpen"></TNavBar>
      </el-header>
      <el-main style="padding: 0px">
        <TTabLayout></TTabLayout>
        <t-footer :footerType="0"></t-footer>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// import TSideBar from "../components/Layout/TSideBar";
import TNavBar from "../components/Layout/TNavBar";
import TTabLayout from "../components/Layout/TTabLayout"
import { mapActions } from "vuex";
// import TFooter from "@/components/T4Cloud/TFooter"
export default {
  data() {
    return {
      isCollapse: false
    }
  },
  components: {
    // TSideBar,
    // TFooter,
    TNavBar,
    TTabLayout
  },
  created() {
    this.getRole()
  },
  methods: {
    ...mapActions(["GetUserRole"]),
    // 展开收缩 隐藏显示
    handleIsOpen() {
      this.isCollapse = !this.isCollapse
    },
    // 获取用户角色
    getRole() {
      this.GetUserRole().then().catch(error => {
        this.$message.error(error.message);
      })
    },
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.el-header {
  background-color: #fff;
}
</style>
