<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['support:SupMessage:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['support:SupMessage:DELETE'])" @onDelete="handleDelete"></t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['support:SupMessage:IMPORT'])"
         :export="hasAuth(['support:SupMessage:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['support:SupMessage:ADD','support:SupMessage:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'support:SupMessage:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

          <!-- 内容  -->
          <template #content="{row}" >
              <t-tool-tip :text="row.content"></t-tool-tip>
          </template>
      </t-standard-table>
      <!-- 编辑modal -->
      <SupMessageModal ref="entityModal" @success="loadData" ></SupMessageModal>
  </div>
</template>
<script>
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SupMessageModal from './modals/SupMessageModal'
// import TTooltip from '@/components/T4Cloud/TTooltip'
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'

export default {
  name:'SupMessageList',
  mixins: [T4CloudListMixin],
  components: {
      // TTooltip,
      // TTableSearch,
      // TTableOperator,
      // TStandardTable,
    SupMessageModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'title',
          name: '消息标题 ',
          query: true,
        },
        {
          key: 'messageType_dict',
          name: '发送方式',
          query: true,
          dict: 'msg_template_type'
        },
        {
          key: 'target',
          name: '接收方',
          query: true,
        },
        {
          key: 'param',
          name: '参数',
        },
        {
          key: 'content',
          name: '内容 ',
          render: 'content'
        },
        {
          key: 'sendTime',
          name: '推送时间 ',
        },
        {
          key: 'sendNum',
          name: '发送次数',
        },
        {
          key: 'sendStatus_dict',
          name: '推送状态',
          query: true,
          dict: 'msg_send_status',
          dictType: 'list',
        },
        // {
        //   key: 'sendResult',
        //   name: '推送失败原因 ',
        // },
        // {
        //   key: 'remark',
        //   name: '备注 ',
        //   query: true,
        // },
      ],
      url: {
        page: '/T4Cloud-Support/SupMessage/page',
        delete: '/T4Cloud-Support/SupMessage/delete',
        exportXls: '/T4Cloud-Support/SupMessage/export',
        importXls: '/T4Cloud-Support/SupMessage/import',
      }
    }
  },
  methods: {}
}
</script>
