<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['user:ExampleCommon:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['user:ExampleCommon:DELETE'])" @onDelete="handleDelete"></t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['user:ExampleCommon:IMPORT'])"
         :export="hasAuth(['user:ExampleCommon:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['user:ExampleCommon:ADD','user:ExampleCommon:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'user:ExampleCommon:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

          <!-- 长文本查询  -->
          <template #txt="{row}" >
              <t-tool-tip :text="row.txt"></t-tool-tip>
          </template>
      </t-standard-table>
      <!-- 编辑modal -->
      <ExampleCommonModal ref="entityModal" @success="loadData" ></ExampleCommonModal>
  </div>
</template>
<script>
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import ExampleCommonModal from './modals/ExampleCommonModal'
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'

export default {
  name:'ExampleCommonList',
  mixins: [T4CloudListMixin],
  components: {
      // TTableSearch,
      // TTableOperator,
      // TStandardTable,
    ExampleCommonModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'str',
          name: '字符串查询 ',
          query: true,
        },
        {
          key: 'txt',
          name: '长文本查询 ',
          render: 'txt'
        },
        {
          key: 'num',
          name: '数字类型查询 ',
          query: true,
          type: 'Number',
          advanceQuery: "ge",
        },
        {
          key: 'queryDate',
          name: '日期查询 ',
          query: true,
          type: 'Date',
        },
        {
          key: 'queryDateTime',
          name: '日期时间查询 ',
          query: true,
          type: 'DateTime',
          advanceQuery: "between"
        },
        {
          key: 'queryTime',
          name: '时间查询 ',
          query: true,
          type: 'Time',
          advanceQuery: "between",
        },
      ],
      url: {
        page: '/T4Cloud-User/ExampleCommon/page',
        delete: '/T4Cloud-User/ExampleCommon/delete',
        exportXls: '/T4Cloud-User/ExampleCommon/export',
        importXls: '/T4Cloud-User/ExampleCommon/import',
      }
    }
  },
  methods: {}
}
</script>
