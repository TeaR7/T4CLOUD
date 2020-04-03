<template>
  <div>
    <el-drawer title="系统设置" :visible.sync="drawer" direction="rtl" :with-header="true" size="300px">
      <div class="setting-drawer-content">
        <div :style="{ marginBottom: '24px' }">
          <h3 class="setting-drawer-title">主题色</h3>

          <div style="height: 20px">
            <el-tooltip class="setting-drawer-theme-color-colorBlock" v-for="(item, index) in colorList" :key="index"
              :content="item.key">
              <el-tag style="width:25px;height:25px;margin-right:10px;" :color="item.color" @click="changeColor(item.color)">
                <!-- <a-icon type="check" v-if="item.color === primaryColor"></a-icon> -->
              </el-tag>
            </el-tooltip>

          </div>
        </div>
        <!-- <a-divider /> -->
      </div>
    </el-drawer>
  </div>
</template>
<script>
import config from '../../defaultSettings'
export default {
  data() {
    return {
      drawer: false,
      primaryColor: '#F5222D',
      colorList: [{
        key: '薄暮', color: '#F5222D',
      },
      {
        key: '火山', color: '#FA541C',
      },
      {
        key: '日暮', color: '#FAAD14',
      }]
    }
  },
  created() {
    console.log(config.primaryColor)
  },
  methods: {
    changeColor(color) {
      if (this.primaryColor !== color) {
        this.$store.dispatch('ToggleColor', color)
        this.updateTheme(color)
      }
    },
    updateTheme(color){
      console.log(color)
    }
  }
}
</script>
<style lang="scss" scoped>
.setting-drawer-content {
  margin: 20px;
  .setting-drawer-title {
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    line-height: 22px;
    margin-bottom: 12px;
  }
}
</style>
