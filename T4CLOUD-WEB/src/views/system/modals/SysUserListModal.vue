<template>
  <div>
    <el-dialog :title="title" :visible.sync="isShow" :before-close="close" :fullscreen="device=='mobile'" @open="loadData">
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
      <!-- 表格区域 -->
      <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
        :url="url" :loading="loading" :btnColumn="false" :export="false"  :import="false"
        @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
        <template #accountHead="{col}">
          <span><i class='el-icon-user'></i>{{col.label}}</span>
        </template>
        <template #account="{col,row}">
          <el-tag>{{row[col.property]}}</el-tag>
        </template>
      </t-standard-table>
      <span slot="footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
// import TTableSearch from '@/components/Table/TTableSearch'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '@/mixins/T4CloudListMixin'
import { T4CloudModalMixin } from '@/mixins/T4CloudModalMixin'

export default {
  name: 'SysUserListModal',
  mixins: [T4CloudListMixin, T4CloudModalMixin],
  components: {
    // TTableSearch,
    // TStandardTable
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'username',
          name: '登录账号',
          query: true,
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
        }, {
          key: 'email',
          name: '邮箱',
        }, {
          key: 'status_dict',
          name: '状态',
          dict: 'common_status',
          dictType: 'radio',
        }
      ],
      url: {
        page: '/T4Cloud-User/SysUser/page',
        save: '/T4Cloud-System/SysUserRole/saveByRole'
      }
    }
  },
  methods: {
    //初始化form表单字段
    initForm() {
      return {
        roleId: null,
        userIds: null,
      }
    },
    beforeSave() {
      this.forms.userIds = this.selectionRowKeys.join(',')
      return true
    }
  }
}
</script>

