<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['user:ProcessLeave:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['user:ProcessLeave:DELETE'])" @onDelete="handleDelete"></t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['user:ProcessLeave:IMPORT'])"
         :export="hasAuth(['user:ProcessLeave:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['user:ProcessLeave:ADD','user:ProcessLeave:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'user:ProcessLeave:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

      </t-standard-table>
      <!-- 编辑modal -->
      <ProcessLeaveModal ref="entityModal" @success="loadData" ></ProcessLeaveModal>
  </div>
</template>
<script>
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import ProcessLeaveModal from './modals/ProcessLeaveModal'

export default {
  name:'ProcessLeaveList',
  mixins: [T4CloudListMixin],
  components: {
    ProcessLeaveModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'processDefinitionId',
          name: '流程定义主键 ',
          query: true,
        },
        {
          key: 'processInstanceId',
          name: '流程实例主键 ',
          query: true,
        },
        {
          key: 'userId',
          name: '用户ID ',
          query: true,
        },
        {
          key: 'userNickname',
          name: '用户姓名 ',
          query: true,
        },
        {
          key: 'startTime',
          name: '开始时间 ',
          query: true,
          type: 'DateTime',
        },
        {
          key: 'endTime',
          name: '结束时间 ',
          query: true,
          type: 'DateTime',
        },
        {
          key: 'reason',
          name: '请假理由 ',
          query: true,
        },
        {
          key: 'applyTime',
          name: '申请时间 ',
          query: true,
          type: 'DateTime',
        },
      ],
      url: {
        page: '/T4Cloud-User/ProcessLeave/page',
        delete: '/T4Cloud-User/ProcessLeave/delete',
        exportXls: '/T4Cloud-User/ProcessLeave/export',
        importXls: '/T4Cloud-User/ProcessLeave/import',
      }
    }
  },
  methods: {}
}
</script>
