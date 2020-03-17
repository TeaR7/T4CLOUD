<template>
    <div>
        <!-- 搜索，可在下方开启 -->
        <TTableSearch :search-col="tableColumn" :query-param="queryParam" @search="search"></TTableSearch>
        <!-- 操作按钮，可直接在标签中定义新按钮 -->
        <TTableOperator :selectedRows="selectionRows" :add="hasAuth(['system:SysDict:ADD'])" @onAdd="handleAdd"
                        :delete="hasAuth(['system:SysDict:DELETE'])" @onDelete="handleDelete"></TTableOperator>
        <!-- 表格区域 -->
        <TStandardTable
                :selectedRows="selectionRows"
                :tableData="tableData"
                :tableColumn="tableColumn"
                :ipagination="ipagination"
                :url="url"
                :loading="loading"
                :import="hasAuth(['system:SysDict:IMPORT'])"
                :export="hasAuth(['system:SysDict:EXPORT'])"
                @pageSizeChange="handPageSizeChange"
                @onSelectRowChange="handSelectRowChange"
                @onTableHeadCommand="handlerTableHeadCommand"
        >
            <!-- 操作按钮 -->
            <template #options="{row}" >
                <el-button type="primary" size="mini" @click="handleEdit(row)"
                           v-auth="['system:SysDict:ADD','system:SysDict:EDIT']">编辑
                </el-button>
                <el-button type="primary" size="mini" @click="handleRelationship(row,'dictId')"
                           v-auth="['system:SysDictValue:ADD','system:SysDictValue:EDIT']">管理字典
                </el-button>
                <el-dropdown>
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down"></i>
              </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="handleDetail(row)">详情</el-dropdown-item>
                        <el-dropdown-item @click.native="handleRelationship(row,'dictId')">管理字典</el-dropdown-item>
                        <el-dropdown-item @click.native="handleDelete([row.id])" v-auth="'system:SysDict:DELETE'">删除</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </template>
        </TStandardTable>
        <!-- 编辑modal -->
        <SysDictModal ref="entityModal" @success="loadData"></SysDictModal>
        <!-- 关联子表编辑（抽屉式） -->
        <SysDictValueList ref="relationshipList"></SysDictValueList>
    </div>
</template>
<script>
    import TTableSearch from '@/components/Table/TTableSearch'
    import TTableOperator from '@/components/Table/TTableOperator'
    import TStandardTable from '@/components/Table/TStandardTable'
    import {T4CloudListMixin} from '../../mixins/T4CloudListMixin'
    import SysDictModal from './modals/SysDictModal'
    import SysDictValueList from './SysDictValueList'

    export default {
        name: 'SysDictList',
        mixins: [T4CloudListMixin],
        components: {
            TTableSearch,
            TTableOperator,
            TStandardTable,
            SysDictValueList,
            SysDictModal
        },
        data() {
            return {
                tableColumn: [
                    {
                        key: 'name',
                        name: '字典名称 ',
                        query: true,
                    },
                    {
                        key: 'code',
                        name: '字典编码 ',
                        query: true,
                    },
                    {
                        key: 'status_dict',
                        name: '删除状态 ',
                    },
                ],
                url: {
                    page: '/T4Cloud-System/SysDict/page',
                    delete: '/T4Cloud-System/SysDict/delete',
                    exportXls: '/T4Cloud-System/SysDict/export',
                    importXls: '/T4Cloud-System/SysDict/import',
                }
            }
        },
        methods: {}
    }
</script>
