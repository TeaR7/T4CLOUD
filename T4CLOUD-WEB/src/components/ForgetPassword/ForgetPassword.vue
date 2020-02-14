<template>
  <div class="forgetPasswordWrap">
    <el-dialog title="修改密码" :visible.sync="isShow">
      <el-form :model="form">
        <el-form-item label="新密码" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <el-form :model="form">
        <el-form-item label="确认新密码" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isShow = false">取 消</el-button>
        <el-button type="primary" @click="sureClick">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      formLabelWidth: '100px',
      form: {
        name: '',
        region: ''
      }
    }
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    sureClick() {
      this.isShow = false
    }
  }
}
</script>

<style lang="scss" scoped>
.forgetPasswordWrap {
}
</style>
