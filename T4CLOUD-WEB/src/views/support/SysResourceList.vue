<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <TTableOperator :selectedRows="selectionRows" :add="hasAuth(['support:SysResource:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['support:SysResource:DELETE'])" @onDelete="handleDelete"></TTableOperator>
      <!-- 表格区域 -->
      <TStandardTable
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['support:SysResource:IMPORT'])"
         :export="hasAuth(['support:SysResource:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['support:SysResource:ADD','support:SysResource:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'support:SysResource:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
      </TStandardTable>
      <!-- 编辑modal -->
      <SysResourceModal ref="entityModal" @success="loadData" ></SysResourceModal>
  </div>
</template>
<script>
import TTableSearch from '@/components/Table/TTableSearch'
import TTableOperator from '@/components/Table/TTableOperator'
import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysResourceModal from './modals/SysResourceModal'

export default {
  name:'SysResourceList',
  mixins: [T4CloudListMixin],
  components: {
    TTableSearch,
    TTableOperator,
    TStandardTable,
    SysResourceModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'name',
          name: '资源名称 ',
          query: true,
        },
        {
          key: 'path',
          name: '相对路径 ',
          query: true,
        },
        {
          key: 'url',
          name: '完整资源路径 ',
          query: true,
        },
        {
          key: 'bucket',
          name: '桶名 ',
          query: true,
        },
        {
          key: 'mimeType',
          name: '资源类型 ',
          query: true,
        },
        {
          key: 'policy',
          name: '权限策略（1-公开，2-私有） ',
          query: true,
          type:'Number',
        },
        {
          key: 'expireTime',
          name: '失效时间 ',
          query: true,
          type:'Date',
        },
        {
          key: 'count',
          name: '总访问次数 ',
          query: true,
          type:'Number',
        },
      ],
      url: {
        page: '/T4Cloud-Support/SysResource/page',
        delete: '/T4Cloud-Support/SysResource/delete',
        exportXls: '/T4Cloud-Support/SysResource/export',
        importXls: '/T4Cloud-Support/SysResource/import',
      }
    }
  },
  methods: {}
}
</script>
