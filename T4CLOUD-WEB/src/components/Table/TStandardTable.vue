<template>
  <div class="standard-table">
    <div class="alert">
      <!-- <el-alert type="info" :show-icon="true"> -->
      <div slot="message">
        已选择&nbsp;<a style="font-weight: 600">{{ selectedRows.length }}</a>项&nbsp;&nbsp;
        <template v-for="(item, index) in needTotalList">
          <!-- v-if="item.needTotal" -->
          {{ item.title }} 总计&nbsp;
          <a :key="index" style="font-weight: 600">
            {{ item.customRender ? item.customRender(item.total) : item.total }}
          </a>&nbsp;&nbsp;
        </template>
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <!-- </el-alert> -->
    </div>
    <!-- <el-table
      :size="size"
      :bordered="bordered"
      :loading="loading"
      :columns="columns"
      :dataSource="current"
      :rowKey="rowKey"
      :pagination="pagination"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: updateSelect }"
    > -->
    <el-table :data="tableData" stripe border v-loading="loading" :header-cell-class-name="headCellStyle">
      <template v-for="item in tableColumn">
        <el-table-column :prop="item.key" :label="item.name" :key="item.key" :width="item.width" align="center">
        </el-table-column>
      </template>
      <template>
        <el-table-column prop="options" label="操作" align="center" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editClick(scope.row.id)">编辑</el-button>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div class="footer-container clearfix">
      <div class="pagination-container fr">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-size="currentSize" :page-sizes="[5, 10, 20, 30, 40]" layout="total, sizes, prev, pager, next, jumper"
          :total="totalSize">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TStandardTable",
  data() {
    return {
      loading: false,
      needTotalList: [],
      selectedRowKeys: [],
      currentPage: 1,
      currentSize: 5,
      totalSize: 0,
    }
  },
  props: {
    tableColumn: {
      type: Array
    },
    tableData: {
      type: Array
    },
    selectedRows: {
      type: Array,
      default: null
    }
  },
  methods: {
    headCellStyle() {
      return 'headCellStyle'
    },
    onClearSelected() {
      this.selectedRowKeys = []
      // this.updateSelect([], [])
    },
    // 编辑
    editClick() {

    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.currentSize = val
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../styles/theme.scss";
.standard-table {
  margin-top: 12px;
  background-color: #fff;
  padding: 12px;
  .alert {
    height: 48px;
    line-height: 48px;
    margin-bottom: 16px;
    background-color: rgba($color: $mainColor, $alpha: 0.2);
    border: 1px solid rgba($color: $mainColor, $alpha: 0.4);
    border-radius: 4px;
    padding: 0px 20px;
  }
  .footer-container {
    margin: 20px 10px 0 0;
    padding-bottom: 20px;
    .pagination-container {
      margin-bottom: 10px;
    }
  }
}
</style>
<style lang="scss">
.el-table .headCellStyle {
  background-color: #eceff4;
}
</style>