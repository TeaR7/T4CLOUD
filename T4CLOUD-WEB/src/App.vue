<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/favicon.ico"> -->
     <router-view />
  </div>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>
/* 引入新的icon，更换链接 */
@import '//at.alicdn.com/t/font_1633797_740ybyibj7y.css';
/* 为了class里面少加iconfont */
[class^="el-icons"], [class^=" el-icons"] {
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
