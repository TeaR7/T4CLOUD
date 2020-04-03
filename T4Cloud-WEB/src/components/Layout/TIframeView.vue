<template>
  <iframe v-if="url.length>0" :id="id" :src="url" frameborder="0" width="100%" height="800px" scrolling="auto"></iframe>
  <div v-else class="emptyDiv">
    当前站点协议为https，不支持内嵌http的页面
  </div>
</template>
<script>
export default {
  name: "TIframeView",
  inject: ['closeCurrentTab'],
  data() {
    return {
      url: "",
      id: ""
    }
  },
  created() {
    this.goUrl()
  },
  updated() {
    this.goUrl()
  },
  watch: {
    $route() {
      // console.log(to, from)
      this.goUrl();
    }
  },
  methods: {
    goUrl() {
      let url = this.$route.meta.url
      let id = this.$route.path
      this.id = id
      if (url !== null && url !== undefined) {
        /*this.$route.meta.openType==1 */
        if (this.$route.meta.openType != undefined && this.$route.meta.openType == 1) {
          this.closeCurrentTab();
          window.open(url);
        } else {
          const protocol = window.location.protocol
          if (protocol == 'http:' || url.indexOf(protocol) >= 0) {
            this.url = url
          } else {
            this.url = ''
          }
        }
      }
    }
  }
}
</script>

<style lang="scss">
.emptyDiv {
  height: 200px;
  line-height: 200px;
  text-align: center;
  font-size: 16px;
}
</style>