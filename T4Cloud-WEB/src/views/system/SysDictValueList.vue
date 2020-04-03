<template>
  <div class="scrollDialog">
    <el-drawer :visible.sync="relationshipVisible" direction="rtl" :size="device=='mobile'?'100%':'900px'"
      :before-close="handleRelationshipClose" @open="loadData">
      <!--     title       -->
      <div slot="title">
        <h2 class="el-drawer__title">{{relationData.name}} - 字典详情管理</h2>
      </div>
      <!-- 搜索，可在下方开启 -->
      <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
      <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <TTableOperator :selectedRows="selectionRows" :add="hasAuth(['system:SysDictValue:ADD'])" @onAdd="handleAdd"
        :delete="hasAuth(['system:SysDictValue:DELETE'])" @onDelete="handleDelete"></TTableOperator>
      <!-- 表格区域 -->
      <TStandardTable :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
        :url="url" :loading="loading" :import="hasAuth(['system:SysDictValue:IMPORT'])" :export="hasAuth(['system:SysDictValue:EXPORT'])"
        @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
        <!-- 操作按钮 -->
        <template #options="{row}">
          <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['system:SysDictValue:ADD','system:SysDictValue:EDIT']">编辑
          </el-button>
          <el-dropdown>
            <span class="el-dropdown-link">
              更多<i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'system:SysDictValue:DELETE'">删除
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
        <!--    字典展示    -->
        <template #text="{col,row}">
          <el-tag>{{row.text}} - {{row.value}}</el-tag>
        </template>
      </TStandardTable>
    </el-drawer>
    <!-- 编辑modal -->
    <SysDictValueModal ref="entityModal" @success="loadData"></SysDictValueModal>
  </div>
</template>
<script>
import TTableSearch from '@/components/Table/TTableSearch'
import TTableOperator from '@/components/Table/TTableOperator'
import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysDictValueModal from './modals/SysDictValueModal'

export default {
  name: 'SysDictValueList',
  mixins: [T4CloudListMixin],
  components: {
    TTableSearch,
    TTableOperator,
    TStandardTable,
    SysDictValueModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'text',
          name: '字典详情',
          render: 'text'
        },
        {
          key: 'status_dict',
          name: '状态',
          width: '100',
        },
      ],
      /* 自定义分页参数 */
      ipagination: {
        pageSize: 20,
      },
      url: {
        page: '/T4Cloud-System/SysDictValue/page',
        delete: '/T4Cloud-System/SysDictValue/delete',
        exportXls: '/T4Cloud-System/SysDictValue/export',
        importXls: '/T4Cloud-System/SysDictValue/import',
      }
    }
  },
  methods: {
  }
}
</script>
<style lang="scss">
</style>

