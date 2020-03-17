<template>
  <div>
    <el-dialog title="图标选择" :visible.sync="show" :fullscreen="device=='mobile'">
      <el-tabs v-model="activeName">
        <el-tab-pane label="浅色系图标" name="first">
          <ul>
            <li v-for="icon in icons.first" :key="icon">
              <!-- <a-icon :type="icon" :title="icon" @click="chooseIcon(icon)" :class="{'active':activeIndex === icon}" /> -->
              <i class="iconClass" :class="'el-icon-'+icon" @click="chooseIcon(icon)"></i>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="深色系图标" name="second">
          <ul>
            <li v-for="icon in icons.second" :key="icon">
              <i class="iconClass" :class="'el-icon-'+icon" @click="chooseIcon(icon)"></i>
            </li>
          </ul>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
const first = ['eleme', 'delete', 'setting', 'user', 'phone-outline', 'more-outline', 'star-off', 'goods', 'warning-outline', 'zoom-in', 'zoom-out', 'remove-outline', 'circle-plus-outline', 'circle-check', 'circle-close', 'help', 'minus', 'plus', 'check', 'close', 'picture-outline', 'picture-outline-round', 'upload2', 'download', 'camera', 'video-camera', 'bell', 's-operation', 'bottom-left', 'bottom-right', 'back', 'right', 'bottom', 'top', 'top-left', 'top-right', 'arrow-left', 'arrow-right', 'arrow-down', 'arrow-up']
const second = ['platform-eleme', 'delete-solid', 's-tools', 'user-solid', 'phone', 'more', 'star-on', 's-goods', 'warning', 'question', 'info', 'remove', 'circle-plus', 'success', 'error', 's-help', 'picture', 'upload', 'camera-solid', 'video-camera-solid', 'message-solid', 's-cooperation', 's-home', 's-release', 's-ticket', 's-management', 's-open', 's-shop', 's-marketing', 's-flag', 's-comment', 's-finance', 's-claim', 's-custom', 's-opportunity', 's-data', 's-check', 's-grid', 'menu', 'share', 'd-caret', 'caret-left', 'caret-right', 'caret-bottom', 'caret-top']
export default {
  data() {
    return {
      show: false,
      activeName: 'first',
      icons: {
        first: first,
        second: second
      }
    }
  },
  computed: {
    ...mapGetters([
      'device'
    ])
  },
  methods: {
    chooseIcon(icon) {
      this.show = false
      this.$emit('onSelectIcon', 'el-icon-' + icon)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../../styles/theme.scss";
ul {
  max-height: 700px;
  overflow-y: auto;
  .iconClass {
    font-size: 1.5rem;
    border: 1px solid #f1f1f1;
    padding: 4px;
    cursor: pointer;
    &.active,
    &:hover {
      border-radius: 2px;
      border-color: $mainColor;
      background-color: $mainColor;
      color: #fff;
      transition: all 0.3s;
    }
  }
  li {
    list-style: none;
    float: left;
    width: 50px;
    height: 50px;
    text-align: center;
    cursor: pointer;
    color: #555;
    transition: color 0.3s ease-in-out, background-color 0.3s ease-in-out;
    position: relative;
    margin: 3px 0;
    border-radius: 4px;
    background-color: #fff;
    overflow: hidden;
    padding: 10px 0 0;
  }
}
</style>
