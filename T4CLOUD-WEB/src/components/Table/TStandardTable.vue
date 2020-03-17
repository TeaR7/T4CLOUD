<template>
  <div class="standard-table" :class="{treeTable: tableType==2}">
    <TTableHeadBtn :columnData="tableColumn" :selectedRows="selectedRows" @onClearTableSelected="clearTableSelected"
      @onTableHeadBtnCommand="tableHeadBtnCommand" :isExport="this.export" :isImport="this.import"></TTableHeadBtn>
    <el-table :data="tableData" stripe border v-loading="loading" :tree-props="treeProps" :header-cell-class-name="headCellStyle"
      :cell-class-name="cellStyle" :row-key="tableType==3?'id':''" ref="multipleTable" @selection-change="handleSelectRowChange"
      size="small">
      <el-table-column v-if="tableType==1||tableType==3" type="selection" width="55" align="center">
      </el-table-column>
      <el-table-column v-if="tableType==2" label="选择" width="55">
        <template slot-scope="scope">
          <el-radio v-model="selectedRows[0]" :label="scope.row"><i></i></el-radio>
        </template>
      </el-table-column>
      <template v-for="item in tableColumn">
        <el-table-column v-if="item.isShow" :prop="item.key" :label="item.name" :key="item.key" :width="item.width"
          :align="item.align?item.align:'center'" :formatter="item.formatter">
          <template slot="header" slot-scope="scope">
            <slot v-if="item.renderHeader" :name="item.renderHeader" :col="scope.column"></slot>
            <span v-else>{{ scope.column.label }}</span>
          </template>
          <template slot-scope="scope">
            <slot v-if="item.render" :name="item.render" :col="scope.column" :row="scope.row"></slot>
            <span v-else>{{scope.row[scope.column.property]}}</span>
          </template>
        </el-table-column>
      </template>
      <template>
        <el-table-column prop="options" label="操作" align="center" class-name="btnColumn">
          <template slot-scope="scope">
            <slot name="options" :col="scope.column" :row="scope.row"></slot>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div v-if="ipagination.show" class="footer-container clearfix">
      <div class="pagination-container fr">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="ipagination.current"
          :page-size="ipagination.pageSize" :page-sizes="ipagination.pageSizeOptions" layout="total, sizes, prev, pager, next, jumper"
          :total="ipagination.total">
        </el-pagination>
      </div>
    </div>
    <TImportExcel ref="importExcel" @onDownLoadTemplate="tableHeadBtnCommand('templteExport')" @onRefreshTable="tableHeadBtnCommand('refresh')"></TImportExcel>
  </div>
</template>

<script>
import TTableHeadBtn from "./TTableHeadBtn"
// import TTableRender from "./TTableRender"
import TImportExcel from './TImportExcel'
export default {
  name: "TStandardTable",
  data() {
    return {
      needTotalList: []
    }
  },
  props: {
    tableType: {
      type: Number,
      default: 1 // 0 光表格 1 多选 2 单选 3 树状
    },
    tableColumn: {
      type: Array
    },
    tableData: {
      type: Array
    },
    loading: {
      type: Boolean
    },
    treeProps: {
      type: Object
    },
    selectedRows: {
      type: Array,
      default() {
        return []
      }
    },
    ipagination: {
      type: Object
    },
    export: { // 导出按钮
      type: Boolean,
      default: true
    },
    import: { // 导入按钮
      type: Boolean,
      default: true
    },
    url: {
      type: Object
    }
  },
  components: {
    TTableHeadBtn,
    // TTableRender,
    TImportExcel
  },
  created() {
    this.selectedRows.forEach(row => {
      this.$refs.multipleTable.toggleRowSelection(row);
    })
  },
  methods: {
    headCellStyle() {
      return 'headCellStyle'
    },
    cellStyle(row) {
      if (row.columnIndex == 1) {
        return 'cellStyle'
      }

    },
    // 导出
    exportClick() {
      this.$emit('onExport')
    },
    // 清除选中的表格
    clearTableSelected() {
      if (this.tableType == 2) {
        this.$emit('onSelectRowChange', [])
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 选择数据回调
    handleSelectRowChange(val) {
      this.$emit('onSelectRowChange', val)
    },
    //分页插件change事件-current
    handleCurrentChange(val) {
      var dic = { ...this.ipagination }
      dic.current = val
      this.$emit('pageSizeChange', dic)
    },
    //分页插件change事件-size
    handleSizeChange(val) {
      var dic = { ...this.ipagination }
      dic.pageSize = val
      this.$emit('pageSizeChange', dic)
    },
    // 修改列表展示列
    tableHeadBtnCommand(type, arr) {
      if (type == 'import') {
        this.handleImportXls()
      } else {
        this.$emit('onTableHeadCommand', type, arr)
      }
    },
    //打开导入组件
    handleImportXls() {
      // console.log("this.url",this.url);
      //判断导入URL
      if (!this.url.importXls) {
        this.$message.error("导入功能需要配置data中url.importXls");
        return;
      }
      this.$refs.importExcel.title = "导入数据";
      this.$refs.importExcel.importXls = this.url.importXls;
      this.$refs.importExcel.isShow = true;
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
  // 更多按钮
  // .el-dropdown-link {
  //   margin-left: 15px;
  //   cursor: pointer;
  //   color: $mainColor;
  // }
  .footer-container {
    margin-top: 20px;
    .pagination-container {
      .el-pagination {
        white-space: pre-wrap;
        text-align: right;
      }
    }
  }
}
</style>
<style lang="scss">
.standard-table {
  .el-table .headCellStyle {
    background-color: #eceff4;
  }
  .el-table {
    z-index: 1;
    .btnColumn {
      font-size: 0;
      .cell {
        margin-left: -10px;
        .el-button {
          margin: 2px 0 2px 10px;
        }
      }
    }
  }
}
.tree-table {
  // 树状表格 没有父级 下拉样式修改
  .el-table .cellStyle {
    .cell span:first-child {
      padding-left: 23px;
    }
  }
}
.pagination-container {
  .el-pagination {
    > * {
      margin-bottom: 10px;
    }
    .el-pagination__jump {
      margin-right: 10px;
    }
  }
}
</style>

