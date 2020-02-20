<template>
  <el-footer :class="classObj">
    Copyright &copy; 2019-2024 Power by
    <a
      class="nameClass"
      href="https://t4cloud.com/"
      target="_blank"
    >T4Cloud</a>
  </el-footer>
</template>

<script>
export default {
  data(){
    return {
      
    }
  },
  props: {
    footerType: {
      type: Number,
      default: 0 // 0 main 1 login
    }
  },
  computed:{
    classObj(){
      return {
        'main-footer': this.footerType===0,
        'login-footer': this.footerType===1
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import '../../styles/element-variables.scss';
.main-footer {
  line-height: 60px;
  text-align: center;
  color: #333;
  font-size: 12px;
  .nameClass{
    color: $--color-primary;
  }
}
.login-footer {
  line-height: 60px;
  width: 100%;
  position: absolute;
  bottom: 0;
  text-align: center;
  color: #ccc;
  font-size: 12px;
   .nameClass{
    color: $--color-primary;
  }
}
</style>