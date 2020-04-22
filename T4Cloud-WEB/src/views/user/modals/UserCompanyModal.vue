<template>
  <div>
    <el-dialog title="选择部门" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'" width="420px">
      <el-checkbox-group class="checkGroupWrap" v-model="companyInfo.current">
        <el-checkbox class="checkBoxWrap" v-for="item in companyInfo.dataRules" :label="item.id" :key="item.id"
          :disabled="userInfo.tenantId==item.id">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <span slot="footer">
        <el-button @click="isShow=false">取消</el-button>
        <el-button type="primary" @click="sureClick">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      isShow: false,
      companyInfo: {
        current: []
      },
      url: {
        save: '/T4Cloud-System/SysCompany/userDataRule'
      }
    }
  },
  computed: {
    ...mapGetters([
      'device',
      'userInfo'
    ])
  },
  methods: {
    // 关闭
    close() {
      this.isShow = false
    },
    // 确定
    sureClick() {
      if (this.companyInfo.current.length < 1) {
        this.$message.warning("请选择用户数据");
        return
      }
      const data = {
        dataRuleIds: this.companyInfo.current.join(',')
      }
      this.$http.httpClient('POST', this.url.save, data).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          window.location.reload()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.checkGroupWrap {
  display: flex;
  flex-direction: column;
  .checkBoxWrap {
    margin-bottom: 10px;
  }
}
</style>

