<template>
  <div>
    <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
    <TTableOperator :selectedRows="selectionRows" @onAdd="handleAdd" :add="hasAuth('user:SysUser:ADD')" @onDelete="handleDelete" :delete="hasAuth('user:SysUser:DELETE')">
    </TTableOperator>
    <!-- 表格区域 -->
    <TStandardTable :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
      :url="url" :loading="loading" :import="hasAuth(['user:SysUser:IMPORT'])" :export="hasAuth(['user:SysUser:EXPORT'])"
      @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
      <template #options="{row}">
        <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['user:SysUser:ADD','user:SysUser:EDIT']">编辑</el-button>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
            <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="['user:SysUser:DELETE']">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
      <template #accountHead="{col}">
        <span><i class='el-icon-user'></i>{{col.label}}</span>
      </template>
      <template #account="{col,row}">
        <el-tag>{{row[col.property]}}</el-tag>
      </template>
    </TStandardTable>
    <SysUserModal ref="entityModal" @success="loadData" :refresh="true"></SysUserModal>
  </div>
</template>
<script>
import TTableSearch from '@/components/Table/TTableSearch'
import TTableOperator from '@/components/Table/TTableOperator'
import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysUserModal from './modals/SysUserModal_Drawer'

export default {
  name: 'SysUserList',
  mixins: [T4CloudListMixin],
  components: {
    TTableSearch,
    TTableOperator,
    TStandardTable,
    SysUserModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'username',
          name: '登录账号',
          query: true,
          width: '200',
          renderHeader: 'accountHead',
          render: 'account'
        }, {
          key: 'realname',
          name: '真实姓名',
          query: true,
        }, {
          key: 'phone',
          name: '电话',
          width: '130',
          query: true,
          type: 'Number'
        }, {
          key: 'email',
          name: '邮箱'
        }, {
          key: 'address',
          name: '地址'
        }, {
          key: 'gender_dict',
          name: '性别',
          query: true,
          dict: 'gender',
          dictType: 'radio',
        }, {
          key: 'status_dict',
          name: '状态',
          query: true,
          dict: 'common_status',
          dictType: 'radio',
        }
      ],
      url: {
        page: '/T4Cloud-User/SysUser/page',
        delete: '/T4Cloud-User/SysUser/delete',
        exportXls: "/T4Cloud-User/SysUser/export",
        importXls: "/T4Cloud-User/SysUser/import"
      }
    }
  },
  methods: {}
}
</script>

