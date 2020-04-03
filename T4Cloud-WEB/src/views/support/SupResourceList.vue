<template>
  <div>
      <!-- 搜索，可在下方开启 -->
      <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
     <!-- 操作按钮，可直接在标签中定义新按钮 -->
      <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['support:SupResource:ADD'])" @onAdd="handleAdd" :delete="hasAuth(['support:SupResource:DELETE'])" @onDelete="handleDelete">
          <!--    此处应该是个可以文件上传的      -->
          <el-button  type="primary" icon="el-icon-upload" @click="handleUpload">文件上传</el-button>
      </t-table-operator>
      <!-- 表格区域 -->
      <t-standard-table
         :selectedRows="selectionRows"
         :tableData="tableData"
         :tableColumn="tableColumn"
         :ipagination="ipagination"
         :url="url"
         :loading="loading"
         :import="hasAuth(['support:SupResource:IMPORT'])"
         :export="hasAuth(['support:SupResource:EXPORT'])"
         @pageSizeChange="handPageSizeChange"
         @onSelectRowChange="handSelectRowChange"
         @onTableHeadCommand="handlerTableHeadCommand"
      >
          <!-- 操作按钮 -->
          <template #options="{row}" >
            <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['support:SupResource:ADD','support:SupResource:EDIT']">编辑</el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="handleDetail(row)" >详情</el-dropdown-item>
                <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'support:SupResource:DELETE'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>

          <!-- 资源路径 -->
          <template #url="{row}" >
              <t-tool-tip :text="row.url"></t-tool-tip>
          </template>

          <!-- 访问次数 -->
          <template #count="{row}" >
              <el-tag>共计：{{row.count}}次</el-tag>
              <el-tag style="margin: 5px;">{{row.updateTime ? row.updateTime : '暂无访问'}}</el-tag>
          </template>

      </t-standard-table>
      <!-- 编辑modal -->
      <SupResourceModal ref="entityModal" @success="loadData" ></SupResourceModal>
      <SupResourceUploadModal ref="SupResourceUploadModal" @success="loadData" ></SupResourceUploadModal>
  </div>
</template>
<script>
// import TTooltip from '@/components/T4Cloud/TTooltip'
// import TTableSearch from '@/components/Table/TTableSearch'
// import TTableOperator from '@/components/Table/TTableOperator'
// import TStandardTable from '@/components/Table/TStandardTable'
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SupResourceModal from './modals/SupResourceModal'
import SupResourceUploadModal from './modals/SupResourceUploadModal'

export default {
  name:'SupResourceList',
  mixins: [T4CloudListMixin],
  components: {
    // TTableSearch, 
    // TTooltip,
    // TTableOperator,
    // TStandardTable,
    SupResourceModal,
    SupResourceUploadModal
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
          key: 'url',
          name: '资源路径 ',
          render: 'url'
        },
        {
          key: 'bucket',
          name: '桶名',
        },
        {
          key: 'mimeType',
          name: '资源类型 ',
          query: true,
        },
        {
          key: 'policy_dict',
          name: '权限',
          query: true,
          dict: 'policy_type',
          dictType:'radio'
        },
        {
          key: 'expireTime',
          name: '失效时间 ',
        },
        {
          key: 'count',
          name: '访问次数/最后访问 ',
          render: 'count'
        },
      ],
      url: {
        page: '/T4Cloud-Support/SupResource/page',
        delete: '/T4Cloud-Support/SupResource/delete',
        exportXls: '/T4Cloud-Support/SupResource/export',
        importXls: '/T4Cloud-Support/SupResource/import',
      }
    }
  },
  methods: {
      handleUpload(){
          this.$refs.SupResourceUploadModal.title = "上传文件资源";
          this.$refs.SupResourceUploadModal.isShow = true;
          this.$refs.SupResourceUploadModal.disableSubmit = false;
      }
  }
}
</script>
