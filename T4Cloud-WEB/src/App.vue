<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/favicon.ico"> -->
    <router-view />
  </div>
</template>

<script>
import device from "t4cloud-util/lib/device";
import { fetchCode } from '@/apis/login'
export default {
  name: "App",
  created() {
    this.getServeTime()
    let loading = document.getElementById("Loading");
    let LoadText = document.getElementById("LoadText");
    if (loading != null) {
      document.body.removeChild(loading);
    }
    if (LoadText != null) {
      document.body.removeChild(LoadText);
    }
    let that = this
    device.deviceEnquire(deviceType => {
      switch (deviceType) {
        case device.DEVICE_TYPE.DESKTOP:
          that.$store.commit('TOGGLE_DEVICE', 'desktop')
          break
        case device.DEVICE_TYPE.TABLET:
          that.$store.commit('TOGGLE_DEVICE', 'tablet')
          break
        case device.DEVICE_TYPE.MOBILE:
        default:
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          break
      }
    })   
  },
  mounted() {
    let loadSection = document.getElementsByClassName("loader-section");
    loadSection[0].classList.add("section-left-hide");
    loadSection[1].classList.add("section-right-hide");
  },
  methods: {
    getServeTime(){
      fetchCode().then(res=>{
        const timestamp = res.timestamp
        const myTimes = Date.parse(new Date())
        this.$store.commit('TOGGLE_TIMES', timestamp-myTimes)
      })
    }
  }
};
</script>

<style>
/* 引入新的icon，更换链接 */
@import "//at.alicdn.com/t/font_1633797_lrbl0z0udgr.css";
/* 为了class里面少加iconfont */
[class^="el-icons"],
[class^=" el-icons"] {
  font-family: "iconfont", serif !important;
  font-size: 18px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center;
  color: #2c3e50; */
  /* margin-top: 60px; */
}
</style>
