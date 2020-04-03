<template>
  <div>
    <div class="timestamp">
      <i class="el-icon-info" style="color: teal"></i>
      上次更新时间：
      <div v-if="device!='mobile'" class="table-time">{{timeStr}}</div>
      <el-divider direction="vertical"></el-divider>
      <el-button
        icon="el-icon-refresh"
        circle
        size="mini"
        style="color: #99cccc;font-size: 12px;"
        @click="manualUpdate"
      ></el-button>
      <div v-if="device=='mobile'">{{timeStr}}</div>
    </div>
    <div :class="device=='mobile' ? 'table-mobile':'table-pc'">
      <el-table
        :data="pageData"
        v-loading="dataLoading"
        element-loading-background="rgba(255, 255, 255, 0.5)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
        ref="multipleTable"
      >
        <el-table-column prop="rtime" label="请求时间" min-width="20%"></el-table-column>
        <el-table-column prop="rtype" label="请求方法" min-width="10%">
          <template slot-scope="scope">
            <el-tag
              v-if="scope.row.rtype === 'GET'"
              effect="dark"
              type="success"
            >{{ scope.row.rtype }}</el-tag>
            <el-tag v-else-if="scope.row.rtype === 'POST'" effect="dark">{{ scope.row.rtype }}</el-tag>
            <el-tag
              v-else-if="scope.row.rtype === 'PUT'"
              effect="dark"
              type="waring"
            >{{ scope.row.rtype }}</el-tag>
            <el-tag
              v-else-if="scope.row.rtype === 'DELETE'"
              effect="dark"
              type="danger"
            >{{ scope.row.rtype }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="rUrl" label="请求URL" min-width="50%"></el-table-column>
        <el-table-column prop="rstatus" label="响应状态" min-width="10%">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.rstatus < 200" light type="info">{{ scope.row.rstatus }}</el-tag>
            <el-tag v-else-if="scope.row.rstatus < 201" type="success">{{ scope.row.rstatus }}</el-tag>
            <el-tag v-else-if="scope.row.rstatus < 399">{{ scope.row.rstatus }}</el-tag>
            <el-tag v-else-if="scope.row.rstatus < 403" type="waring">{{ scope.row.rstatus }}</el-tag>
            <el-tag v-else-if="scope.row.rstatus < 501" type="danger">{{ scope.row.rstatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="rconsume" label="请求耗时" min-width="10%">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.rconsume < 500" type="success">{{ scope.row.rconsume }}ms</el-tag>
            <el-tag v-else-if="scope.row.rconsume < 1000">{{ scope.row.rconsume }}ms</el-tag>
            <el-tag v-else-if="scope.row.rconsume < 1500" type="waring">{{ scope.row.rconsume }}ms</el-tag>
            <el-tag v-else type="danger">{{ scope.row.rconsume }}ms</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination
          v-if="totalData.length>0"
          background
          :page-size="pageSize"
          :page-sizes="[5, 10, 15, 20]"
          :current-page="currentPage"
          @current-change="handleCurrentChang"
          @size-change="handleSizeChange"
          layout="total, sizes, prev, pager, next"
          :total="totalData.length"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  let requestLive; //计时器

export default {
  name: "RequestTab",
  props: ["dynamicUrl"],
  computed: {
    ...mapGetters(["device"])
  },
  data() {
    return {
      pageData: [],
      totalData: [],
      resData: [],
      serviceUrl: "T4Cloud-Support",
      requestUrl: "/actuator/httptrace",
      hasService: true,
      dataLoading: false,
      timeStr: "",
      //分页用参数
      currentPage: 1,
      pageSize: 10
    };
  },
  methods: {
    getRequestList() {
      let finalUrl = "/" + this.serviceUrl + this.requestUrl;
      this.dataLoading = true;
      setTimeout(() => {
        this.$http.GET(finalUrl, null).then(
          res => {
            if (res) {
              this.dataLoading = false;
              // this.requestData = res.traces;
              //数据整合
              this.dataSettle(res.traces);
              this.resData = res.traces;
            }
            //时间戳处理
            this.timeStr = this.timeTrans(new Date());
          },
          err => {
            //err处理
            this.dataLoading = false;
            console.log(err);
          }
        );
      }, 1500);
    },
    //自动刷新数据
    autoUpdate() {
      this.getRequestList();
      // 实时信息计时30s调用接口
      requestLive = self.setInterval(() => {
        this.getRequestList();
      }, 30000);
    },
    //手动刷新数据
    manualUpdate() {
      this.getRequestList();
    },
    stopTimer() {
      window.clearInterval(requestLive);
    },
    dataSettle(resArr) {
      for (let i of resArr) {
        i.rtime = this.timeDatas(i.timestamp.epochSecond * 1000);
        i.rtype = i.request.method;
        // if (i.request.headers["referer"]) {
        //   i.rUrl = i.request.headers["referer"];
        // } else {
          i.rUrl = i.request.uri;
        // }
        i.rstatus = i.response.status;
        i.rconsume = i.timeTaken;
      }
      this.totalData = resArr;
      this.pageData = resArr.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
    handleCurrentChang(currentPage) {
      this.currentPage = currentPage;
      this.pageData = this.totalData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.dataSettle(this.resData);
    },
    //时间格式转换
    timeDatas(time) {
      let date = new Date(time);
      let Y = date.getFullYear();
      let M =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let m =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let s =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return Y + "-" + M + "-" + D + " " + h + ":" + m + ":" + s;
    },
    //时间戳格式转换
    timeTrans(time) {
      let date = new Date(time);
      let Y = date.getFullYear();
      let M =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let m =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let s =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return (
        Y + "年" + M + "月" + D + "日" + " " + h + "时" + m + "分" + s + "秒"
      );
    }
  },
  watch: {
    dynamicUrl: {
      deep: true,
      handler(newValue) {
        if (newValue) this.serviceUrl = newValue;
        //改变服务器触发
        this.getRequestList();
      }
    }
  }
};
</script>

<style lang="scss">
.page-box {
  padding: 10px 0;
  float: right;
}
.table-time {
  display: inline;
}
</style>