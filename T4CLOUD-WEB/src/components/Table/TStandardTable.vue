<template>
  <div class="standard-table">
    <div class="alert">
      <!-- <el-alert type="info" :show-icon="true"> -->
      <div slot="message">
        已选择&nbsp;<a style="font-weight: 600">{{ selectedRows.length }}</a>项&nbsp;&nbsp;
<!--        <template v-for="(item, index) in needTotalList">-->
<!--          &lt;!&ndash; v-if="item.needTotal" &ndash;&gt;-->
<!--          {{ item.title }} 总计&nbsp;-->
<!--          <a :key="index" style="font-weight: 600">-->
<!--            {{ item.customRender ? item.customRender(item.total) : item.total }}-->
<!--          </a>&nbsp;&nbsp;-->
<!--        </template>-->
        <a style="margin-left: 24px" @click="handleClearTableSelected">清空</a>
        <!-- <el-popover placement="top" width="160" v-model="visible">
          <p>确定删除吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="visible = false">确定</el-button>
          </div>
          <el-button slot="reference" type="primary" size="mini">删除</el-button>
        </el-popover> -->
      </div>

      <!-- </el-alert> -->
    </div>

    <el-table :data="tableData" stripe border v-loading="loading" :header-cell-class-name="headCellStyle" ref="multipleTable"
      @selection-change="handleSelectRowChange">
      <el-table-column type="selection" width="55" align="center">
      </el-table-column>
      <template v-for="item in tableColumn">
        <el-table-column :prop="item.key" :label="item.name" :key="item.key" :width="item.width" align="center">
        </el-table-column>
      </template>
      <template>
        <el-table-column prop="options" label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editClick(scope.row)">编辑</el-button>
            <el-popover placement="top" width="160" v-model="scope.row.deleteVisible">
              <p>确定删除吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini"  @click="scope.row.deleteVisible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="deleteClick(scope.row.id)">确定</el-button>
              </div>
              <el-button slot="reference" type="primary" size="mini" style="margin-left:10px;">删除</el-button>
            </el-popover>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div class="footer-container clearfix">
      <div class="pagination-container fr">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="ipagination.current"
          :page-size="ipagination.pageSize" :page-sizes="ipagination.pageSizeOptions" layout="total, sizes, prev, pager, next, jumper"
          :total="ipagination.total">
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
      needTotalList: []
    }
  },
  props: {
    tableColumn: {
      type: Array
    },
    tableData: {
      type: Array
    },
    loading: {
      type: Boolean
    },
    selectedRows: {
      type: Array,
      default() {
        return []
      }
    },
    ipagination: {
      type: Object
    }
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
    // 编辑
    editClick(record) {
      this.$emit('onEdit', record)
    },
    // 删除
    deleteClick(id) {
      this.deleteVisible = false
      this.$emit('onDelete', [id])
    },
    // 清除选中的表格
    handleClearTableSelected() {
      this.$refs.multipleTable.clearSelection();
    },
    // 选择数据回调
    handleSelectRowChange(val) {
      this.$emit('onSelectRowChange', val)
    },
    //分页插件change事件-current
    handleCurrentChange(val) {
      this.$emit('pageSizeChange', {
        current: val,
        pageSize: this.ipagination.pageSize,
      })
    },
    //分页插件change事件-size
    handleSizeChange(val) {
      this.$emit('pageSizeChange', {
        current: this.ipagination.current,
        pageSize: val,
      })
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
