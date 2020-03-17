<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <TTableOperator :selectedRows="selectionRows" :add="hasAuth(['Support:SysMessageTemplate:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['Support:SysMessageTemplate:DELETE'])" @onDelete="handleDelete"></TTableOperator>
      <!-- 表格区域 -->
      <TStandardTable
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['Support:SysMessageTemplate:IMPORT'])"
         :export="hasAuth(['Support:SysMessageTemplate:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['Support:SysMessageTemplate:ADD','Support:SysMessageTemplate:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'Support:SysMessageTemplate:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
      </TStandardTable>
      <!-- 编辑modal -->
      <SysMessageTemplateModal ref="entityModal" @success="loadData" ></SysMessageTemplateModal>
  </div>
</template>
<script>
import TTableSearch from '@/components/Table/TTableSearch'
import TTableOperator from '@/components/Table/TTableOperator'
import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysMessageTemplateModal from './modals/SysMessageTemplateModal'

export default {
  name:'SysMessageTemplateList',
  mixins: [T4CloudListMixin],
  components: {
    TTableSearch,
    TTableOperator,
    TStandardTable,
    SysMessageTemplateModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'templateCode',
          name: '模板CODE ',
          query: true,
        },
        {
          key: 'templateName',
          name: '模板标题 ',
          query: true,
        },
        {
          key: 'templateType',
          name: '模板类型：1短信 2邮件 3微信 ',
          query: true,
        },
        {
          key: 'templateContent',
          name: ' templateContent  ',
          query: true,
        },
      ],
      url: {
        page: '/T4Cloud-Support/SysMessageTemplate/page',
        delete: '/T4Cloud-Support/SysMessageTemplate/delete',
        exportXls: '/T4Cloud-Support/SysMessageTemplate/export',
        importXls: '/T4Cloud-Support/SysMessageTemplate/import',
      }
    }
  },
  methods: {}
}
</script>
