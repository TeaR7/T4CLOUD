<template>
  <div>
    <t-table-search :search-col="tableColumn" :query-param="queryParam" :query-perfix-param="prefixQueryParam" @search="search"></t-table-search>
    <t-table-operator :selectedRows="selectionRows" @onAdd="handleAdd" :add="hasAuth('user:SysUser:ADD')" @onDelete="handleDelete"
      :delete="hasAuth('user:SysUser:DELETE')">
    </t-table-operator>
    <!-- 表格区域 -->
    <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
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
      <template #avatar="{col,row}">
        <t-image :width="'45px'" :height="'45px'" :url="row.avatar" :srcList="[row.avatar]">
        </t-image>
      </template>
    </t-standard-table>
    <SysUserModal ref="entityModal" @success="loadData" :refresh="true"></SysUserModal>
  </div>
</template>
<script>
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysUserModal from './modals/SysUserModal_Drawer'
// import  TImage  from '../../components/T4Cloud/TImage'
export default {
  name: 'SysUserList',
  mixins: [T4CloudListMixin],
  components: {
    // TTableSearch,
    // TTableOperator,
    // TStandardTable,
    SysUserModal,
    // TImage
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'username',
          name: '登录账号',
          query: true,
          advanceQuery: "ne",
          width: '150',
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
          query: true
        }, {
          key: 'email',
          name: '邮箱'
        }, {
          key: 'gender_dict',
          name: '性别',
          query: true,
          dict: 'gender',
          dictType: 'radio',
        }, {
          key: 'avatar',
          name: '头像',
          render: 'avatar'
        }, {
          key: 'birthday',
          name: '生日',
          query: true,
          type: 'Date',
          advanceQuery: "eq"
        }, {
          key: 'createTime',
          name: '注册时间',
          query: true,
          type: 'DateTime',
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

