<template>
  <div>
    <TTableOperator :selectedRows="selectionRows" @onAdd="handleAdd" @onDelete="handleDelete">
    </TTableOperator>
    <!-- 表格区域 -->
    <div class="treeTable">
      <div class="alert">
        <div slot="message">
          已选择&nbsp;<a style="font-weight: 600">{{ selectionRows.length }}</a>项&nbsp;&nbsp;
          <a style="margin-left: 24px" @click="handleClearTableSelected">清空</a>
        </div>
      </div>

      <el-table ref="multipleTable" :data="tableData" row-key="id" stripe border v-loading="loading"
        :header-cell-class-name="headCellStyle" @selection-change="handSelectRowChange" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column type="selection" width="45" align="center">
        </el-table-column>
        <template v-for="item in tableColumn">
          <el-table-column :prop="item.key" :label="item.name" :key="item.key" :width="item.width" :align="item.align?'item.align':'center'">
          </el-table-column>
        </template>
        <template>
          <el-table-column prop="options" label="操作" align="center" width="160">
            <template slot-scope="scope">
              <el-button v-authz="'system:SysPermission:EDITs'" type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-dropdown @command="handleTableCommand">
                <span class="el-dropdown-link">
                  更多<i class="el-icon-arrow-down"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item :command="{name:'detail',record: scope.row}">详情</el-dropdown-item>
                  <el-dropdown-item :command="{name:'addSub',record: scope.row}">添加子菜单</el-dropdown-item>
                  <el-dropdown-item :command="{name:'delete',record: scope.row}">删除</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </template>
      </el-table>
    </div>
    <SysPermissionModal ref="entityModal" @success="loadData" :tableData="tableData"></SysPermissionModal>
  </div>
</template>
<script>
import TTableOperator from '../../components/Table/TTableOperator'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysPermissionModal from './modals/SysPermissionModal'
export default {
  mixins: [T4CloudListMixin],
  components: {
    TTableOperator,
    SysPermissionModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'name',
          name: '菜单名称',
          width: '170',
          align: 'left',
          query: true,
          type: String
        }, {
          key: 'menuType',
          name: '菜单类型',
          width: '100',
          query: true,
          type: String
        }, {
          key: 'icon',
          name: 'icon',
          width: '100'
        }, {
          key: 'component',
          name: '组件'
        }, {
          key: 'url',
          name: '路径'
        },
        {
          key: 'sortNo',
          name: '排序',
          width: '70'
        }
      ],
      url: {
        tree: '/T4Cloud-System/SysPermission/tree',
        delete: '/T4Cloud-System/SysPermission/delete',
      }
    }
  },
  methods: {
    headCellStyle() {
      return 'headCellStyle'
    },
    // 清除选中的表格
    handleClearTableSelected() {
      this.$refs.multipleTable.clearSelection();
    },
    // 表格 下拉菜单点击事件
    handleTableCommand(command) {
      if(command.name=='detail'){
        this.handleDetail(command.record)
      } else if(command.name=='addSub'){
        this.handleAddSub(command.record)
      }
      else if(command.name=='delete'){
        this.handleDelete([command.record.id])
      }
    },
    // 添加子菜单
    handleAddSub(record){
      this.$refs.entityModal.title = "添加子菜单";
      this.$refs.entityModal.disableSubmit = false;
      this.$refs.entityModal.edit({menuType: 1, 'parentId':record.id});
    }
  }
}
</script>
<style lang="scss">
.el-table .headCellStyle {
  background-color: #eceff4;
}
</style>
<style lang="scss" scoped>
@import "../../styles/theme.scss";
.table-operator {
  background-color: #fff;
  margin-top: 12px;
  padding: 15px 20px;
  .upload-demo {
    margin-left: 10px;
    display: inline-block;
  }
}
.treeTable {
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
  //
  .el-dropdown-link {
    margin-left: 15px;
    cursor: pointer;
    color: $mainColor;
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
