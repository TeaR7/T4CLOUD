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
      tableColumn: [{
        key: 'date',
        name: '日期',
        width: '150'
      }, {
        key: 'name',
        name: '姓名',
        width: '120'
      }, {
        key: 'address',
        name: '地址'
      }, {
        key: 'sex',
        name: '性别',
        width: '100'
      }, {
        key: 'status',
        name: '状态',
        width: '100'
      }],
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          sex: '男',
          status: '活跃'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
          sex: '男',
          status: '活跃'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
          sex: '男',
          status: '活跃'
        }
      ]
    }
  }
}
</script>
