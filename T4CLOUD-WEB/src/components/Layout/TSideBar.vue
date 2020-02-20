<template>
  <div class="sideBarWrap">
    <div v-if="device=='desktop'">
      <div class="headImgDiv">
        <div class="headImgBox">
          <img src="@/assets/logos/logo-o-w.png" alt="logo" />
          <div class="text" :class="!isCollapse?'open':'close'">T4CLOUD</div>
        </div>
      </div>
      <el-menu class="el-menu-vertical-demo" :default-active="$route.path" :default-openeds="['1']" background-color="#1d2128"
        text-color="#fff" active-text-color="#ffd04b" :collapse="isCollapse" :collapse-transition="true" router>
        <TSidebarItem :routes='addRouters[0].children'></TSidebarItem>
      </el-menu>
    </div>
    <div v-else>
      <el-drawer title="" :visible.sync="isCollapse" direction="ltr" :with-header="false" size="auto"
        :before-close="handleClose">
        <div class="sideBarWrap">
          <div class="headImgDiv">
            <div class="headImgBox">
              <img src="@/assets/logos/logo-o-w.png" alt="logo" />
              <div class="text open">T4CLOUD</div>
            </div>
          </div>
          <el-menu class="el-menu-vertical-demo" :default-active="$route.path" :default-openeds="['1']"
            background-color="#1d2128" text-color="#fff" active-text-color="#ffd04b"
            :collapse-transition="true" router @select="mobileMenuSelect">
            <TSidebarItem :routes='addRouters[0].children'></TSidebarItem>
          </el-menu>
    </div>
    </el-drawer>
  </div>
  </div>
</template>
<script>
import TSidebarItem from '../Layout/TSidebarItem'
import { mapGetters } from 'vuex'
export default {
  name: "SideBar",
  props: {
    isCollapse: {
      type: Boolean,
      default: false
    }
  },
  components: { TSidebarItem },
  computed: {
    ...mapGetters([
      'addRouters',
      'device'
    ])
  },
  methods: {
    handleClose() {
      this.$emit('hideSideBar')
    },
    mobileMenuSelect(){
      this.$emit('hideSideBar')
    }
  }
};
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  /* height: 100%; */
}
.el-menu {
  border-right: none !important;
}
</style>
<style lang="scss" scoped>
.sideBarWrap {
  background-color: #1d2128;
  height: 100%;
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



