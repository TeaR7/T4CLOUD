<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['support:SupMessageTemplate:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['support:SupMessageTemplate:DELETE'])" @onDelete="handleDelete"></t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['support:SupMessageTemplate:IMPORT'])"
         :export="hasAuth(['support:SupMessageTemplate:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['support:SupMessageTemplate:ADD','support:SupMessageTemplate:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'support:SupMessageTemplate:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

          <!-- 访问次数 -->
          <template #templateContent="{row}" >
              <t-tool-tip :text="row.templateContent"></t-tool-tip>
          </template>

      </t-standard-table>
      <!-- 编辑modal -->
      <SupMessageTemplateModal ref="entityModal" @success="loadData" ></SupMessageTemplateModal>
  </div>
</template>
<script>
    // import TTooltip from '@/components/T4Cloud/TTooltip'
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SupMessageTemplateModal from './modals/SupMessageTemplateModal'

export default {
  name:'SupMessageTemplateList',
  mixins: [T4CloudListMixin],
  components: {
      // TTooltip,
    // TTableSearch,
    // TTableOperator,
    // TStandardTable,
    SupMessageTemplateModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'templateCode',
          name: 'CODE',
          query: true,
        },
        {
          key: 'templateName',
          name: '标题',
          query: true,
        },
        {
          key: 'templateType_dict',
          name: '模板类型',
          query: true,
          dict: 'msg_template_type'
        },
          {
              key: 'templateContent',
              name: '模板内容',
              render: 'templateContent'
          },
      ],
      url: {
        page: '/T4Cloud-Support/SupMessageTemplate/page',
        delete: '/T4Cloud-Support/SupMessageTemplate/delete',
        exportXls: '/T4Cloud-Support/SupMessageTemplate/export',
        importXls: '/T4Cloud-Support/SupMessageTemplate/import',
      }
    }
  },
  methods: {}
}
</script>
