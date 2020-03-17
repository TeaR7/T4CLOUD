<template>
  <div class="importXls">
    <el-dialog title="导入数据" :visible.sync="isShow" :fullscreen="device=='mobile'" @close="dialogClose" width="35%">
      <el-upload ref="upload" action="" accept=".xls,.xlsx" :on-remove="handleRemove" :limit="1" :before-upload="beforeUpload"
        :file-list="fileList" :auto-upload="false" :http-request="importXlsApi" style="text-align:center">
        <el-button slot="trigger" type="primary">选取文件</el-button>
        <div class="el-upload__tip">*只能上传一个xls/xlsx文件</div>
      </el-upload>
      <div class="botBtnDiv">
        <el-button class="el-upload__down" type="text" size="small" @click="downTemplateClick">下载模版</el-button>
        <span slot="footer" class="dialog-footer">
          <el-button @click="close" size="small">取 消</el-button>
          <el-button type="primary" @click="submitUpload" size="small">导入</el-button>
        </span>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { upload } from "../../utils/http"
export default {
  name: 'TImportExcel',
  data() {
    return {
      importXls: '/T4Cloud-User/SysUser/import',
      fileList: [],
      isShow: false
    }
  },
  computed: {
    ...mapGetters([
      'device'
    ])
  },
  methods: {
    // 弹框关闭 回调
    dialogClose() {
      this.fileList = []
    },
    beforeUpload: function (file) {
      let filename = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = filename === 'xls'
      const extension2 = filename === 'xlsx'
      if (!extension && !extension2) {
        this.$message({
          message: '上传文件只能是 xls、xlsx格式!',
          type: 'warning'
        });
      }
      return extension || extension2
    },
    // 导入
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 移除文件
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 上传文件请求
    importXlsApi(params) {
      var formData = new FormData();
      formData.append('file', params.file);
      upload(this.importXls, formData).then(res => {
        if (res.success) {
          this.$message.success(res.message);
          this.$emit("onRefreshTable")
          this.close()
        } else {
          this.$message.warning(res.message);
        }
      })
    },
    // 下载模版 指定行传-1
    downTemplateClick() {
      this.$emit('onDownLoadTemplate')
      this.close()
    },
    //关闭窗口
    close() {
      this.isShow = false
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../../styles/theme";
.importXls {
  .el-upload__tip {
    margin-top: 15px;
    color: $warningColor;
  }
  .el-upload__down {
    margin-left: 20px;
  }
  .botBtnDiv{
    margin-top: 30px;
    display: flex;
    justify-content: space-between;
  }
}
</style>
