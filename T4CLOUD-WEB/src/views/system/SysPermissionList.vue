<template>
  <div class="sysPermissionList">
    <TTableOperator :selectedRows="selectionRows" @onAdd="handleAdd" @onDelete="handleDelete">
      <template>
        <!-- v-if="hasAuth(['system:SysPermission:EDIT']) -->
        <!-- v-auth="'system:SysPermission:EDIT'" -->
        <el-button type="primary" icon="el-icon-plus" v-role="'ADMIN'">创建单据</el-button>
      </template>
    </TTableOperator>
    <!-- 表格区域 -->
    <TStandardTable :tableType="3" :tableData="tableData" :tableColumn="tableColumn" :selectedRows="selectionRows"
      :ipagination="ipagination" :loading="loading" :treeProps="treeProps" @onSelectRowChange="handSelectRowChange"
      @onTableHeadCommand="handlerTableHeadCommand">
      <template #name="{row}" >
        <el-tag><i :class="row.icon"></i> {{row.name}}</el-tag>
      </template>
      <template #options="{row}" >
        <el-button type="primary" size="mini" @click="handleEdit(row)">编辑</el-button>
        <el-dropdown >
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
            <el-dropdown-item @click.native="handleAddSub(row)">添加子项</el-dropdown-item>
            <el-dropdown-item @click.native="handleDelete([row.id])">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>

    </TStandardTable>

    <SysPermissionModal ref="entityModal" @success="loadData" :tableData="tableData"></SysPermissionModal>
  </div>
</template>
<script>
import TTableOperator from '../../components/Table/TTableOperator'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import TStandardTable from '../../components/Table/TStandardTable'
import SysPermissionModal from './modals/SysPermissionModal'
export default {
  mixins: [T4CloudListMixin],
  components: {
    TTableOperator,
    TStandardTable,
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
