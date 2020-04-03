<template>
  <div class="sysPermissionList">
    <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['system:SysPermission:ADD'])" @onAdd="handleAdd"
      :delete="hasAuth(['system:SysPermission:DELETE'])" @onDelete="handleDelete">
    </t-table-operator>
    <!-- 表格区域 -->
    <t-standard-table :tableType="3" :tableData="tableData" :tableColumn="tableColumn" :selectedRows="selectionRows"
      :ipagination="ipagination" :loading="loading" :treeProps="treeProps" @onSelectRowChange="handSelectRowChange"
      @onTableHeadCommand="handlerTableHeadCommand" :import="hasAuth(['system:SysPermission:IMPORT'])" :export="hasAuth(['system:SysPermission:EXPORT'])">
      <template #name="{row}">
        <el-tag><i :class="row.icon"></i> {{row.name}}</el-tag>
      </template>
      <template #options="{row}">
        <el-button type="primary" size="mini" v-auth="['system:SysPermission:ADD','system:SysPermission:EDIT']" @click="handleEdit(row)">编辑</el-button>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
            <el-dropdown-item @click.native="handleAddSub(row)" v-auth="'system:SysPermission:ADD'">添加子项</el-dropdown-item>
            <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'system:SysPermission:DELETE'">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>

    </t-standard-table>

    <SysPermissionModal ref="entityModal" @success="loadData" :tableData="tableData"></SysPermissionModal>
  </div>
</template>
<script>
// import TTableOperator from '../../components/Table/TTableOperator'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
// import TStandardTable from '../../components/Table/TStandardTable'
import SysPermissionModal from './modals/SysPermissionModal'
export default {
  mixins: [T4CloudListMixin],
  components: {
    // TTableOperator,
    // TStandardTable,
    SysPermissionModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'name',
          name: '菜单名称',
          width: '220',
          align: 'left',
          render: 'name',
          query: true,
          type: String
        }, {
          key: 'menuType',
          name: '菜单类型',
          width: '100',
          query: true,
          type: String
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
  created() {
    this.ipagination.show = false
  },
  methods: {}
}
</script>
