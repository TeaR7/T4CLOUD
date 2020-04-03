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
    <div class="echart-box">
      <div id="redisEchartId" class="echart-pic"></div>
    </div>
    <div class="table-title">Redis 状态记录</div>
    <div :class="device=='mobile' ? 'table-mobile':'table-pc'">
      <el-table
        :data="tableData"
        v-loading="dataLoading"
        element-loading-background="rgba(255, 255, 255, 0.5)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
      >
        <el-table-column prop="key" label="Key" min-width="40%">
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.key}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="Description" min-width="40%"></el-table-column>
        <el-table-column prop="value" label="Value" min-width="20%"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";
import { mapGetters } from "vuex";

var redisLive; //redis计时器

export default {
  name: "RedisTab",
  props: ["dynamicUrl"],
  computed: {
    ...mapGetters(["device"])
  },
  data() {
    return {
      tableData: [],
      lineData: [],
      serviceUrl: "T4Cloud-Support",
      infoUrl: "/actuator/redis/info",
      liveInfoUrl: "/actuator/redis/liveInfo",
      echartArrs: {
        xtimeArr: [],
        ykeySizeArr: [],
        ymemoryArr: []
      },
      hasService: true,
      dataLoading: false,
      timeStr: "",
      echartsOptions: {
        title: {
          text: "Redis 监控信息"
        },
        legend: {
          data: ["Key(个)", "Memory(MB)"]
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985"
            }
          }
        },
        dataZoom: [
          {
            type: "slider",
            show: false,
            xAxisIndex: [0],
            start: 21,
            end: 100,
            zoomLock: true
          },
          {
            type: "inside",
            xAxisIndex: [0],
            start: 21,
            end: 100,
            zoomLock: true
          }
        ],
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "Key(个)",
            data: [],
            type: "line",
            areaStyle: {}
          },
          {
            name: "Memory(MB)",
            data: [],
            type: "line",
            areaStyle: {}
          }
        ]
      }
    };
  },
  methods: {
    //redis运行信息
    getRedisInfo() {
      let finalUrl = "/" + this.serviceUrl + this.infoUrl;
      this.dataLoading = true;
      setTimeout(() => {
        this.$http.GET(finalUrl, null).then(
          res => {
            if (res.success) {
              this.dataLoading = false;
              this.tableData = res.result;
              this.timeStr = this.timeTrans(res.timestamp);
            }
          },
          err => {
            console.log(err);
            //err处理
            this.dataLoading = false;
            //未开通服务
            this.tableData = [];
          }
        );
      }, 1000);
    },
    //redis实时存储信息
    getRedisLiveInfo() {
      let finalUrl = "/" + this.serviceUrl + this.liveInfoUrl;
      this.$http.GET(finalUrl, null).then(
        res => {
          if (res.success) {
            this.lineData = res.result;
            this.hasService = true;
            //先重置数据
            this.echartArrs = {
              xtimeArr: [],
              ykeySizeArr: [],
              ymemoryArr: []
            };
            //数据处理
            for (let i of this.lineData) {
              i["xtime"] = i["timestamp"].split(" ")[1];
              this.echartArrs.xtimeArr.push(i["xtime"]);
              this.echartArrs.ykeySizeArr.push(i["keySize"]);
              this.echartArrs.ymemoryArr.push(i["memory"]);
            }
            //绘制折线图
            this.drawEchart();
          }
        },
        err => {
          //err处理
          let errRes = err.response;
          if (errRes.status) {
            //未开通服务
            this.hasService = false;
            this.echartArrs = {
              xtimeArr: [],
              ykeySizeArr: [],
              ymemoryArr: []
            };
          }
          //绘制折线图
          this.drawEchart();
        }
      );
    },
    drawEchart() {
      let vm = this;
      vm.redisChart = echarts.init(document.getElementById("redisEchartId"));
      vm.redisChart.setOption(vm.echartsOptions);
      window.onresize = vm.redisChart.resize;
      vm.redisChart.showLoading({
        text: "loading...",
        maskColor: "rgba(255,255,255,0.5)",
        textColor: "#333",
        color: "teal"
      });
      setTimeout(() => {
        vm.echartsOptions.xAxis["data"] = vm.echartArrs.xtimeArr;
        vm.echartsOptions.series[0]["data"] = vm.echartArrs.ykeySizeArr;
        vm.echartsOptions.series[1]["data"] = vm.echartArrs.ymemoryArr;
        //mobile show
        if (vm.device == "mobile") {
          vm.echartsOptions.legend = {
            left: 50,
            top: "10%",
            data: ["Key(个)", "Memory(MB)"]
          };
        }
        vm.redisChart.setOption(vm.echartsOptions);
        window.onresize = vm.redisChart.resize;
        vm.redisChart.hideLoading();
        if (vm.hasService === false) {
          vm.redisChart.showLoading({
            text: "实时监控服务暂不可用",
            color: "#ffffff",
            textColor: "#333",
            fontSize: "28"
            // maskColor: "rgba(255, 255, 255, 0.8)"
          });
        } else {
          vm.redisChart.hideLoading();
        }
      }, 1000);
    },
    //自动刷新数据
    autoUpdate() {
      this.getRedisLiveInfo();
      this.getRedisInfo();
      // 实时信息计时30s调用接口
      redisLive = self.setInterval(() => {
        this.getRedisLiveInfo();
        this.getRedisInfo();
      }, 30000);
    },
    //手动刷新数据
    manualUpdate() {
      this.getRedisInfo();
      this.getRedisLiveInfo();
      // 实时信息计时30s调用接口
      window.clearInterval(redisLive);
      redisLive = self.setInterval(() => {
        this.getRedisLiveInfo();
      }, 30000);
    },
    stopTimer() {
      window.clearInterval(redisLive);
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
        this.getRedisInfo();
        this.getRedisLiveInfo();
      }
    }
  },
  mounted() {}
};
</script>

<style lang="scss">
.echart-box {
  height: 320px;
  width: 100%;
  .echart-pic {
    width: 100%;
    height: 100%;
  }
}
.table-title {
  font-weight: bold;
  color: #333;
  font-size: 20px;
  padding-left: 8px;
}

.timestamp {
  background-color: rgba(0, 128, 128, 0.2);
  border: 1px solid rgba(0, 128, 128, 0.4);
  padding-left: 20px;
  padding-top: 2px;
  padding-bottom: 2px;
  font-weight: 700;
  font-size: 14px;
  margin-bottom: 20px;
}
.table-time {
  display: inline;
}
</style>
