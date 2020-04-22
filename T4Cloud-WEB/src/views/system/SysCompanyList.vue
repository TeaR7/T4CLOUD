<template>
  <div>
    <!-- 搜索，可在下方开启 -->
    <t-table-search :search-col="tableColumn" :query-param="queryParam" @search="search"></t-table-search>
    <!-- 操作按钮，可直接在标签中定义新按钮 -->
    <t-table-operator :selectedRows="selectionRows" :add="hasAuth(['system:SysCompany:ADD'])" @onAdd="handleAdd"
      :delete="hasAuth(['system:SysCompany:DELETE'])" @onDelete="handleDelete"></t-table-operator>
    <!-- 表格区域 -->
    <t-standard-table :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn" :ipagination="ipagination"
      :url="url" :loading="loading" :import="hasAuth(['system:SysCompany:IMPORT'])" :export="hasAuth(['system:SysCompany:EXPORT'])"
      @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange" @onTableHeadCommand="handlerTableHeadCommand">

      <!--    租户编号      -->
      <template #status="{row}">
        <div>
          {{row.status_dict}}
        </div>
        <el-tag>
          {{row.id}}
        </el-tag>
      </template>

      <!--    公司名称      -->
      <template #name="{row}">
        <el-tag>
          {{row.nameAbbr}}
        </el-tag>
        <div>
          {{row.name}}
        </div>
      </template>

      <!-- 操作按钮 -->
      <template #options="{row}">
        <el-button type="primary" size="mini" @click="handleEdit(row)" v-auth="['system:SysCompany:ADD','system:SysCompany:EDIT']">编辑</el-button>
        <el-button type="primary" size="mini" @click="handleRelationship(row,'companyId')" v-auth="['system:SysUserRole:VIEW']">用户</el-button>
        <el-dropdown>
          <span class="el-dropdown-link">
            更多<i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
            <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'system:SysCompany:DELETE'">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>

      <!-- 描述  -->
      <template #description="{row}">
        <t-tool-tip :text="row.description"></t-tool-tip>
      </template>
    </t-standard-table>
    <!-- 编辑modal -->
    <SysCompanyModal ref="entityModal" @success="loadData"></SysCompanyModal>
    <!-- 关联内表 -->
    <SysCompanyUserModal ref="relationshipList"></SysCompanyUserModal>
  </div>
</template>
<script>
import { T4CloudListMixin } from '../../mixins/T4CloudListMixin'
import SysCompanyModal from './modals/SysCompanyModal'
import SysCompanyUserModal from './modals/SysCompanyUserModal'

export default {
  name: 'SysCompanyList',
  mixins: [T4CloudListMixin],
  components: {
    SysCompanyModal,
    SysCompanyUserModal
  },
  data() {
    return {
      tableColumn: [
        // {
        //     key: 'id',
        //     name: '租户编号',
        //     query: true,
        //     width: '260px',
        //     render:'id'
        // },
        {
          key: 'name',
          name: '公司',
          query: true,
          render: 'name'
        },
        // {
        //   key: 'nameEn',
        //   name: '英文名 ',
        //   query: true,
        // },
        // {
        //   key: 'nameAbbr',
        //   name: '公司名缩写 ',
        //   query: true,
        // },
        {
          key: 'description',
          name: '描述',
          render: 'description'
        },
        {
          key: 'phone',
          name: '联系方式 ',
          query: true,
        },
        // {
        //   key: 'fax',
        //   name: '传真 ',
        // },
        // {
        //   key: 'address',
        //   name: '地址 ',
        //   query: true,
        // },
        // {
        //   key: 'remark',
        //   name: '备注 ',
        // },
        {
          key: 'status_dict',
          name: '状态 - 租户ID',
          render: 'status'
        },
      ],
      url: {
        page: '/T4Cloud-System/SysCompany/page',
        delete: '/T4Cloud-System/SysCompany/delete',
        exportXls: '/T4Cloud-System/SysCompany/export',
        importXls: '/T4Cloud-System/SysCompany/import',
      }
    }
  },
  methods: {}
}
</script>
