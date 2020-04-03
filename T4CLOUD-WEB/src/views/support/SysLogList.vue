<template>
  <div>
    <!-- 搜索，可在下方开启 -->
    <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
    <!-- 操作按钮，可直接在标签中定义新按钮 -->
    <t-table-operator :selectedRows="selectionRows" :add="false" @onAdd="handleAdd" :delete="hasAuth(['support:SysLog:DELETE'])"
      @onDelete="handleDelete"></t-table-operator>
    <!-- 表格区域 -->
    <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
      :url="url" :loading="loading" :import="false" :export="hasAuth(['support:SysLog:EXPORT'])" @pageSizeChange="handPageSizeChange"
      @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">
      <!-- 操作按钮 -->
      <template #options="{row}">
        <el-button type="primary" size="mini" @click="handleDetail(row)">详情</el-button>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <!--                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>-->
            <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'support:SysLog:DELETE'">删除
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>

      <!-- 日志类型 -->
      <template #logType="{row}">
        <el-tag>{{row.logType_dict}}</el-tag>
      </template>

      <!-- 操作类型 -->
      <template #operateType="{row}">
        <el-tag :type="row.operateType == 1 ? 'success' : row.operateType == 2 ? 'info' :row.operateType == 3 ? 'warning' :row.operateType == 4 ? '' : 'danger'">
          {{row.operateType_dict}}
        </el-tag>
      </template>

      <!-- 执行结果 -->
      <template #result="{row}">
        <t-tool-tip :text="row.result"></t-tool-tip>
      </template>

      <!-- 执行结果 -->
      <template #costTime="{row}">
        <div>
          <el-tag>{{row.costTime}} ms</el-tag>
        </div>
        <div>
          <el-tag style="margin-top: 5px">{{row.createTime}}</el-tag>
        </div>
      </template>

    </t-standard-table>
    <!-- 编辑modal -->
    <SysLogModal ref="entityModal" @success="loadData"></SysLogModal>
  </div>
</template>
<script>
// import TTooltip from '@/components/T4Cloud/TTooltip'
// import TTableSearch from '@/components/Table/TTableSearch'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysLogModal from './modals/SysLogModal'

export default {
  name: 'SysLogList',
  mixins: [T4CloudListMixin],
  components: {
    // TTableSearch,
    // TTooltip,
    // TStandardTable,
    SysLogModal
  },
  data() {
    return {
      tableColumn: [
        {
          key: 'logType_dict',
          name: '日志类型',
          query: true,
          dict: 'log_type',
          render: 'logType'
        },
        {
          key: 'logContent',
          name: '日志内容 ',
          query: true,
        },
        {
          key: 'operateType_dict',
          name: '操作类型',
          query: true,
          dict: 'operate_type',
          render: 'operateType'
        },
        {
          key: 'result',
          name: '操作结果记录 ',
          render: 'result'
          // tooltip: true // 兼容性不好
        },
        {
          key: 'resultType_dict',
          name: '执行状态',
          query: true,
          dict: 'common_valid',
        },
        // {
        //     key: 'username',
        //     name: '操作用户名称',
        //     query: true,
        // },
        // {
        //     key: 'ip',
        //     name: 'IP ',
        //     query: true,
        // },
        // {
        //     key: 'method',
        //     name: '请求java方法 ',
        // },
        {
          key: 'requestUrl',
          name: '请求路径 ',
          query: true,
        },
        // {
        //     key: 'requestParam',
        //     name: '请求参数 ',
        //     query: true,
        // },
        {
          key: 'requestType',
          name: '请求类型 ',
          query: true,
        },
        {
          key: 'costTime',
          name: '耗时 / 发生时间',
          render: 'costTime'
        },
      ],
      url: {
        page: '/T4Cloud-Support/SysLog/page',
        delete: '/T4Cloud-Support/SysLog/delete',
        exportXls: '/T4Cloud-Support/SysLog/export',
        importXls: '/T4Cloud-Support/SysLog/import',
      }
    }
  },
  methods: {}
}
</script>
