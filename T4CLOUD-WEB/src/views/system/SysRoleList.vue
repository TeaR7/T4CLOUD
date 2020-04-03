<template>
  <div>
    <!-- 搜索，可在下方开启 -->
    <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
    <!-- 操作按钮，可直接在标签中定义新按钮 -->
    <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['system:SysRole:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['system:SysRole:DELETE'])"
      @onDelete="handleDelete"></t-table-operator>
    <!-- 表格区域 -->
    <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
      :url="url" :loading="loading" :import="hasAuth(['system:SysRole:IMPORT'])" :export="hasAuth(['system:SysRole:EXPORT'])"
      @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
      <!-- 操作按钮 -->
      <template #options="{row}">
        <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['system:SysRole:ADD','system:SysRole:EDIT']">编辑</el-button>
        <el-button type="primary" size="mini" @click="handleAuth(row)" v-auth="['system:SysRolePermission:VIEW']">授权</el-button>
        <el-button type="primary" size="mini" @click="handleRelationship(row,'roleId')" v-auth="['system:SysUserRole:VIEW']">用户</el-button>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
            <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'system:SysRole:DELETE'">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </t-standard-table>
    <!-- 编辑modal -->
    <SysRoleModal ref="entityModal" @success="loadData"></SysRoleModal>
    <!-- 授权modal -->
    <SysRolePermModal ref="rolePermModal" @success="loadData"></SysRolePermModal>
    <!--  -->
    <SysRoleUserModal ref="relationshipList"></SysRoleUserModal>
  </div>
</template>
<script>
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysRoleModal from './modals/SysRoleModal'
import SysRolePermModal from './modals/SysRolePermModal'
import SysRoleUserModal from './modals/SysRoleUserModal_Drawer'

export default {
  name: 'SysRoleList',
  mixins: [T4CloudListMixin],
  components: {
    // TTableSearch,
    // TTableOperator,
    // TStandardTable,
    SysRoleModal,
    SysRolePermModal,
    SysRoleUserModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'roleName',
          name: '角色名称 ',
          query: true,
        },
        {
          key: 'roleCode',
          name: '角色编码 ',
          query: true,
        },
        {
          key: 'description',
          name: '描述 ',
        },
      ],
      url: {
        page: '/T4Cloud-System/SysRole/page',
        delete: '/T4Cloud-System/SysRole/delete',
        exportXls: '/T4Cloud-System/SysRole/export',
        importXls: '/T4Cloud-System/SysRole/import',
      }
    }
  },
  methods: {
    // 授权点击事件
    handleAuth(row) {
      this.$refs.rolePermModal.isShow = true;
      this.$refs.rolePermModal.title = "角色权限配置";
      this.$refs.rolePermModal.disableSubmit = this.hasAuth(['system:SysRole:EDIT']);
      this.$refs.rolePermModal.getList({
        id: null,
        roleId: row.id,
        permissionIds: []
      })
    }
  }
}
</script>
