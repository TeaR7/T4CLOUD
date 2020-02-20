<template>

  <div>
    <div class="searchDiv">
        <span class="fromSpan">订单编号：</span>
        <el-input class="customInput" v-model="searchOrderNo" placeholder="请输入订单编号" clearable></el-input>
        <span class="fromSpan">酒店名称： </span>
        <el-input class="customInput" v-model="searchHotelName" placeholder="请输入酒店名称" clearable></el-input>
          <span class="fromSpan">状态： </span>
      <el-select v-model="selectStatus" placeholder="请选择" @change="getOrderList">
        <el-option v-for="item in statusList" :key="item.id" :label="item.text" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="searchBtn" type="primary" @click="getOrderList">查询</el-button>
    </div>
    <div class="tableDiv">
      <el-table :data="orderList" stripe  border v-loading="loading" :header-cell-class-name="headCellStyle">
        <el-table-column prop="OrderNo" label="订单编号" align="center"></el-table-column>
        <el-table-column prop="HotelName" label="酒店名称" align="center"></el-table-column>
        <el-table-column prop="PeopleNum" label="人数" align="center"></el-table-column>
        <el-table-column prop="ContactMobile" label="客户信息" align="center"></el-table-column>
        <el-table-column label="活动时间" width="190" align="center">
          <template slot-scope="scope">
             <span>{{scope.row.ActivityStartTime.split(' ')[0]}}~{{scope.row.ActivityEndTime.split(' ')[0]}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="StatusTxt" label="状态" align="center"></el-table-column>
        <el-table-column prop="options" label="操作" align="center">
          <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="cancelOrder(scope.row.id)">客服咨询</el-button>
        </template>
        </el-table-column>
      </el-table>
      <div class="footer-container clearfix">
        <div class="pagination-container fr">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-size="currentSize" :page-sizes="[5, 10, 20, 30, 40]" layout="total, sizes, prev, pager, next, jumper" :total="totalSize">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import { fetchOrderList } from '../api/orderManage'
export default {
  data () {
    return {
      message: '',
      orderList: [{
        OrderNo: '2019110356535199',
        HotelName: '香格里拉大酒店',
        PeopleNum: '10～30',
        ContactMobile: '13817353023',
        ActivityStartTime: '2019-12-15 00:00:00',
        ActivityEndTime: '2019-12-15 00:00:00',
        StatusID: '10',
        StatusTxt: '咨询中'
      }, {
        OrderNo: '2019110356535199',
        HotelName: '香格里拉大酒店',
        PeopleNum: '10～30',
        ContactMobile: '13817353023',
        ActivityStartTime: '2019-12-15 00:00:00',
        ActivityEndTime: '2019-12-15 00:00:00',
        StatusID: '10',
        StatusTxt: '咨询中'
      }, {
        OrderNo: '2019110356535199',
        HotelName: '香格里拉大酒店',
        PeopleNum: '10～30',
        ContactMobile: '13817353023',
        ActivityStartTime: '2019-12-15 00:00:00',
        ActivityEndTime: '2019-12-15 00:00:00',
        StatusID: '10',
        StatusTxt: '咨询中'
      }],
      currentPage: 1,
      currentSize: 5,
      totalSize: 0,
      searchOrderNo: '',
      searchHotelName: '',
      selectStatus: '',
      loading: false,
      statusList: [{
        id: '',
        text: '全部'
      }, {
        id: 10,
        text: '咨询中'
      }, {
        id: 20,
        text: '已确认'
      }, {
        id: 30,
        text: '已完成'
      }, {
        id: 40,
        text: '已过期'
      }, {
        id: 50,
        text: '已取消'
      }]
    }
  },
  created () {
    this.getOrderList()
  },
  methods: {
    headCellStyle () {
      return 'headCellStyle'
    },
    getOrderList () {
      // this.loading = true
     
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.getOrderList()
    },
    handleSizeChange (val) {
      this.currentSize = val
      this.getOrderList()
    }
  }
}
</script>

<style lang="scss" scoped>
.searchDiv{
  background-color: #fff;
  // margin: 12px;
  margin-bottom: 12px;
  padding: 15px 20px;
  font-size: 15px;
  .customInput{
    width: 250px;
    margin-right: 10px;
  }
  .searchBtn{
    margin-left: 10px;
  }
}
.tableDiv{
   background-color: #fff;
   padding: 15px 20px;
}
.footer-container {
  margin: 20px 10px 0 0;
  padding-bottom: 20px;
}
.pagination-container {
  margin-bottom: 10px;
}

</style>
<style lang="scss">
.tableDiv {
  .el-table .headCellStyle {
    background-color: #eceff4;
  }
}
</style>
