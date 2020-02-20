<template>
  <div class="table-search">
    <div class="lineDiv">
      <span class="lineSpan">订单编号：</span>
      <el-input class="lineInput" placeholder="请输入订单编号" clearable></el-input>
    </div>
    <div class="lineDiv">
      <span class="lineSpan">酒店名称： </span>
      <el-input class="lineInput" placeholder="请输入酒店名称" clearable></el-input>
    </div>
    <!-- <span class="fromSpan">状态： </span>
    <el-select v-model="selectStatus" placeholder="请选择" @change="getOrderList">
      <el-option v-for="item in statusList" :key="item.id" :label="item.text" :value="item.id">
      </el-option>
    </el-select> -->
    <el-button type="primary">查询</el-button>
  </div>
</template>
<style lang="scss" scoped>
.table-search {
  background-color: #fff;
  padding: 15px 20px;
  font-size: 15px;
  .lineDiv {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    .lineSpan {
      flex-shrink: 0;
    }
   
  }
}
@media screen and (min-width: 650px) {
  .table-search {
    .lineDiv {
      margin-bottom: 0px;
      display: inline-block;
      margin-right: 20px;
      .lineSpan {
        display: inline-block;
      }
      .lineInput {
        width: 250px;
      }
    }
  }
}
</style>
