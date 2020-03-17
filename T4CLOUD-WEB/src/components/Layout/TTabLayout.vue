<template>
  <div class="tab-layout">
    <el-tabs v-model="activePage" @tab-click="handleClick" closable @tab-remove="removeTab">
      <el-tab-pane v-for="item in pageList" :key="item.path" :label="item.name" :name="item.path">
        <!-- {{item.content}} -->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
const indexKey = '/dashboard/analysis'
export default {
  data() {
    return {
      pageList: [],
      linkList: [],
      activePage: '',
      lastActivePage: '',
    }
  },
  watch: {
    '$route': function (newRoute) {
      this.activePage = newRoute.fullPath
      if (this.linkList.indexOf(newRoute.fullPath) < 0) {
        this.linkList.push(newRoute.fullPath)
        this.pageList.push(Object.assign({}, newRoute))
      } else if (this.linkList.indexOf(newRoute.fullPath) >= 0) {
        let oldIndex = this.linkList.indexOf(newRoute.fullPath)
        let oldPositionRoute = this.pageList[oldIndex]
        this.pageList.splice(oldIndex, 1, Object.assign({}, newRoute, { meta: oldPositionRoute.meta }))
      }
    },
    'activePage': function (newKey) {
      this.lastActivePage = newKey
    }
  },
  created() {
    if (this.$route.path != indexKey) {
      this.pageList.push({
        name: '首页',
        path: indexKey,
        fullPath: indexKey,
        meta: {
          icon: 'dashboard',
          title: '首页'
        }
      })
      this.linkList.push(indexKey)
    }
    this.pageList.push(this.$route)
    this.linkList.push(this.$route.fullPath)
    this.activePage = this.$route.fullPath
  },
  methods: {
    // tab点击事件
    handleClick(tab) {
      if (tab.paneName != this.lastActivePage) {
        let index = this.linkList.lastIndexOf(tab.paneName)
        let waitRouter = this.pageList[index]
        this.$router.push(Object.assign({}, waitRouter));
      }
    },
    // 删除标签
    removeTab(key) {
      if (key == indexKey) {
        this.$message.warning('首页不能关闭!')
        return
      }
      // if (this.pageList.length === 1) {
      //   this.$message.warning('这是最后一页，不能再关闭了啦')
      //   return
      // }
      this.pageList = this.pageList.filter(item => item.fullPath !== key)
      let index = this.linkList.indexOf(key)
      this.linkList = this.linkList.filter(item => item !== key)
      index = index >= this.linkList.length ? this.linkList.length - 1 : index
      this.activePage = this.linkList[index]

      // let index = this.linkList.lastIndexOf(tab.paneName)
      let waitRouter = this.pageList[index]
      this.$router.push(Object.assign({}, waitRouter));

    }
  }
}
</script>
<style lang="scss" scoped>
.tab-layout {
  margin-top: 5px;
  background-color: #fff;
  height: 45px;
  line-height: 45px;

  // padding: 0px 20px;
}
</style>
<style lang="scss">
.tab-layout {
  .el-tabs__nav {
    left: 20px;
  }
  .el-tabs__item {
    font-size: 13px;
  }
}
</style>

