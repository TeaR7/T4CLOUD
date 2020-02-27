<template>
  <div>
    <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
    <TTableOperator :selectedRows="selectionRows" @onAdd="handleAdd" @onDelete="handleDelete">
    </TTableOperator>

    <!-- 操作按钮区域 -->
    <!--    <div class="table-operator">-->
    <!--      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>-->
    <!--      <el-dropdown v-if="selectionRows.length>0" split-button type="primary" @command="handleDelete" style="margin-left:10px;">-->
    <!--        批量操作-->
    <!--        <el-dropdown-menu slot="dropdown">-->
    <!--          <el-dropdown-item command="delete">删除</el-dropdown-item>-->
    <!--        </el-dropdown-menu>-->
    <!--      </el-dropdown>-->
    <!--    </div>-->
    <!-- 表格区域 -->
    <TStandardTable :selectedRows="selectionRows" :tableData="tableData" :tableColumn="tableColumn"
                    :ipagination="ipagination" :loading="loading"
                    @pageSizeChange="handPageSizeChange" @onSelectRowChange="handSelectRowChange"
                    @onEdit="handleEdit" @onDelete="handleDelete"></TStandardTable>
    <SysUserModal ref="entityModal" @success="loadData"></SysUserModal>
  </div>
</template>
<script>
  import TTableSearch from '../../components/Table/TTableSearch'
  import TTableOperator from '../../components/Table/TTableOperator'
  import TStandardTable from '../../components/Table/TStandardTable'
  import {T4CloudListMixin} from '../../mixins/T4CloudListMixin'
  import SysUserModal from './modals/SysUserModal'

  export default {
    mixins: [T4CloudListMixin],
    components: {
      TTableSearch,
      TTableOperator,
      TStandardTable,
      SysUserModal
    },
    data() {
      return {
        tableColumn: [
          {
            key: 'username',
            name: '登录账号',
            width: '150',
            query: true,
            type: String
          }, {
            key: 'realname',
            name: '真实姓名',
            width: '120',
            query: true,
            type: String
          }, {
            key: 'phone',
            name: '电话',
            width: '130',
            query: true,
            type: Number
          }, {
            key: 'email',
            name: '邮箱',
            width: '200'
          }, {
            key: 'address',
            name: '地址',
            // width: '100'
          }
        ],
        url: {
          page: '/T4Cloud-User/SysUser/page',
          delete: '/T4Cloud-User/SysUser/delete',
        }
      }
    },
    methods: {}
  }
</script>
<style lang="scss" scoped>
  .table-operator {
    background-color: #fff;
    margin-top: 12px;
    padding: 15px 20px;

    .upload-demo {
      margin-left: 10px;
      display: inline-block;
    }
  }

  @media screen and (max-width: 650px) {
    .table-operator {
      .el-button {
        margin-bottom: 10px;
      }
    }
  }
</style>
