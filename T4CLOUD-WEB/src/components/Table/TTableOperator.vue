<template>
  <!-- 操作按钮区域 -->
  <div class="table-operator">
    <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
    <el-button type="primary" icon="el-icon-plus" @click="handJump">创建单据</el-button>
    <el-button type="primary" icon="el-icon-plus" @click="handOneToMany">一对多</el-button>
    <el-button type="primary" icon="el-icon-download2" @click="handleExportXls('demo')">导出</el-button>
    <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :file-list="fileList"
      :show-file-list="false">
      <el-button type="primary" icon="el-icon-download">导入</el-button>
    </el-upload>
    <el-dropdown split-button type="primary" style="margin-left:10px;">
      批量操作
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>删除</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>
<script>
export default {
  data() {
    return {
      fileList: []
    }
  },
  methods: {
    handleAdd() {

    },
    handJump() {

    },
    handOneToMany() {

    },
    handleExportXls() {

    }
  }
}
</script>
<style lang="scss" scoped>
.table-operator {
  background-color: #fff;
  margin-top: 12px;
  padding: 15px 20px;
  .upload-demo {
    margin-left: 10px;
    display: inline-block;
  }
}
@media screen and (max-width: 650px) {
  .table-operator {
    .el-button {
      margin-bottom: 10px;
    }
  }
}
</style>




