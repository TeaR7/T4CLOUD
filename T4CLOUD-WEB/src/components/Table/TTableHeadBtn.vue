<template>
  <div class="alert">
    <div class="selectDiv">
      已选择&nbsp;<a style="font-weight: 600">{{ selectedRows.length }}</a>项&nbsp;&nbsp;
      <a class="clearSpan" @click="clearTableSelected">清空</a>
    </div>
    <div class="btnGroup" id="test">
      <el-popover trigger="click">
        <el-table ref="multipleSelectTable" :data="columnData" @select="handleRowSelect" @select-all="handleRowSelectAll" size="mini">
          <el-table-column type="selection" width="45" align="center">
          </el-table-column>
          <el-table-column width="100" property="name" label="全选"></el-table-column>
        </el-table>
        <el-button class="firstBtn" slot="reference" type="primary" size="small" plain icon="el-icon-search"></el-button>
      </el-popover>
      <el-tooltip v-if="isExport" effect="dark" content="导出" placement="top-start">
        <el-button class="midBtn" type="primary" size="small" plain icon="el-icon-download" @click="exportClick"></el-button>
      </el-tooltip>
      <el-tooltip v-if="isImport" class="item" effect="dark" content="导入" placement="top-start">
        <el-button class="midBtn" type="primary" size="small" plain icon="el-icon-upload2" @click="importClick"></el-button>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top-start">
        <el-button class="lastBtn" type="primary" size="small" plain icon="el-icon-refresh" @click="refreshClick"></el-button>
      </el-tooltip>
    </div>
  </div>

</template>
<script>
export default {
  name: 'TTableHeadBtn',
  data() {
    return {
    }
  },
  props: {
    isExport: {
      type: Boolean,
      default: true
    },
    isImport: {
      type: Boolean,
      default: true
    },
    columnData: {
      type: Array
    },
    selectedRows: {
      type: Array,
      default() {
        return []
      }
    },
  },
  watch: {
    columnData: {
      handler(newData) {
        this.initData(newData)
      }
    }
  },
  created() {
    this.initData(this.columnData)
  },

  methods: {
    // 初始化表格选中数据
    initData(arr) {
      this.$nextTick(function () {
        arr.forEach(row => {
          if (row.isShow == undefined || row.isShow) {
            row.isShow = true
            this.$refs.multipleSelectTable.toggleRowSelection(row);
          }
        })
      })
    },
    // 导出
    exportClick() {
      this.$emit('onTableHeadBtnCommand', 'export')
    },
    // 导入
    importClick() {
      this.$emit('onTableHeadBtnCommand', 'import')
    },
    // 刷新
    refreshClick() {
      // console.log(e)
      // document.getElementById("test").focus()
      this.$emit('onTableHeadBtnCommand', 'refresh')
    },
    // 清空
    clearTableSelected() {
      this.$emit('onClearTableSelected')
    },
    // 单个选中事件
    handleRowSelect(val) {
      let tempArr = this.columnData
      tempArr.forEach(temp => {
        temp.isShow = false
        val.forEach(item => {
          if (item.key == temp.key) {
            temp.isShow = true
          }
        })
      });
      this.$emit('onTableHeadBtnCommand', 'tableColumn', tempArr)
    },
    // 全选
    handleRowSelectAll(val) {
      let tempArr = this.columnData
      tempArr.forEach(item => {
        item.isShow = val.length != 0
      })
      this.$emit('onTableHeadBtnCommand', 'tableColumn', tempArr)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../../styles/theme.scss";
.alert {
  display: flex;
  justify-content: space-between;
  // align-items: center;
  height: 40px;
  line-height: 40px;
  margin-bottom: 16px;
  background-color: rgba($color: $mainColor, $alpha: 0.2);
  border: 1px solid rgba($color: $mainColor, $alpha: 0.4);
  border-radius: 4px;
  padding: 0px 20px;
  .selectDiv {
    font-size: 14px;
    .clearSpan {
      margin-left: 24px;
    }
  }
  .btnGroup {
    /deep/.el-button.is-plain:hover{
      background: teal !important;
      border-color: teal !important;
      color: #fff !important;
    }
    /deep/.el-button--primary.is-plain:focus{
      color: teal;
      background: #e6f2f2;
      border-color: #99cccc;
    }
    margin-top: -2px;
    .firstBtn {
      border-right-color: #fff;
      border-top-right-radius: 0;
      border-bottom-right-radius: 0;
    }
    .midBtn {
      margin-right: 0px;
      margin-left: 0px;
      border-left: none;
      border-right-color: #fff;
      border-radius: 0;
    }
    .lastBtn {
      margin-left: 0px;
      border-left: none;
      border-top-left-radius: 0;
      border-bottom-left-radius: 0;
    }
  }
}
@media screen and (max-width: 650px) {
  .alert {
    padding: 0 8px;
    .selectDiv {
      .clearSpan {
        margin-left: 10px;
      }
    }
  }
}
</style>
