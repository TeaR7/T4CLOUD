<template>
  <div>
    <TTableSearch></TTableSearch>
    <TTableOperator></TTableOperator>
    <TStandardTable :selectedRows="array" :tableData="tableData" :tableColumn="tableColumn"></TStandardTable>
  </div>
</template>
<script>
import TTableSearch from '../../components/Table/TTableSearch'
import TTableOperator from '../../components/Table/TTableOperator'
import TStandardTable from '../../components/Table/TStandardTable'
export default {
  components: {
    TTableSearch,
    TTableOperator,
    TStandardTable
  },
  data() {
    return {
      array: [],
      tableColumn: [
        {
          key: 'roleId',
          name: '角色id ',
        },
        {
          key: 'permissionId',
          name: '权限id ',
        },
      ],
      tableData: [
        {
          'roleId' : '角色id测试数据',
          'permissionId' : '权限id测试数据',
        }
      ]
    }
  }
}
</script>
