<template>
  <div class="noFixedDrawer">
    <el-drawer :visible.sync="relationshipVisible" direction="rtl" :size="device=='mobile'?'100%':'550px'"
      :before-close="handleRelationshipClose" @open="openClick">
      <div slot="title">
        <h2 class="el-drawer__title">公司用户配置</h2>
      </div>
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
      <t-table-operator :selectedRows="selectionRows" @onAdd="handleAdd" :add="false" @onDelete="deleteClick" :delete="hasAuth('user:SysUser:DELETE')">
        <el-button type="primary" icon="el-icon-plus" v-role="'ADMIN'" @click="addUserClick">添加已有用户</el-button>
      </t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
        :url="url" :loading="loading" :import="hasAuth(['user:SysUser:IMPORT'])" :export="hasAuth(['user:SysUser:EXPORT'])"
        @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
        <template #options="{row}">
          <el-button type="primary" size="mini" @click="deleteClick([row.id])" v-auth="['user:SysUser:DELETE']">删除</el-button>
        </template>
        <template #accountHead="{col}">
          <span><i class='el-icon-user'></i>{{col.label}}</span>
        </template>
        <template #account="{col,row}">
          <el-tag>{{row[col.property]}}</el-tag>
        </template>
      </t-standard-table>
    </el-drawer>
    <SysUserListModal ref="entityModal" @success="loadData"></SysUserListModal>
  </div>
</template>
<script>
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'
import SysUserListModal from './SysUserListModal'
import { T4CloudListMixin } from '@/mixins/T4CloudListMixin'

export default {
  name: 'SysUserList',
  mixins: [T4CloudListMixin],
  components: {
    // TTableSearch,
    // TTableOperator,
    // TStandardTable,
    SysUserListModal
  },
  data() {
    return {
      openRefresh: false,
      tableColumn: [
        {
          key: 'username',
          name: '登录账号',
          query: true,
          renderHeader: 'accountHead',
          render: 'account'
        }, {
          key: 'realname',
          name: '真实姓名'
        }, {
          key: 'status_dict',
          name: '状态',
          dict: 'common_status',
          dictType: 'radio',
        }
      ],
      url: {
        page: '/T4Cloud-System/SysUserCompany/getUserList/',
        delete: '/T4Cloud-System/SysUserCompany/delete',
        exportXls: "/T4Cloud-User/SysUser/export",
        importXls: "/T4Cloud-User/SysUser/import"
      }
    }
  },
  methods: {
    // 弹框打开
    openClick() {
      this.url.page = '/T4Cloud-System/SysUserCompany/getUserList/' + this.relationData.id
      this.loadData()
    },
    // 添加已有用户
    addUserClick() {
      this.$refs.entityModal.isShow = true
      this.$refs.entityModal.title = '添加已有用户'
      this.$refs.entityModal.url.save = '/T4Cloud-System/SysUserCompany/save'
      this.$refs.entityModal.edit({
        companyId: this.relationData.id
      })
    },
    // 删除
    deleteClick(idArr) {
      const params = {
        userIds: idArr.join(","),
        companyId: this.relationData.id
      }
      this.handleDelete(idArr, params)
    }
  }
}
</script>