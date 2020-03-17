<template>
  <div>
    <div class="timestamp">
      <i class="el-icon-info" style="color: teal"></i>
      上次更新时间：{{timeStr}}
      <el-divider direction="vertical"></el-divider>
      <el-button
        icon="el-icon-refresh"
        circle
        size="mini"
        style="color: #99cccc;font-size: 12px;"
        @click="manualUpdate"
      ></el-button>
    </div>
    <div class="table-title">服务器配置</div>
    <div class="cpu-table">
      <el-table
        :data="tableData"
        v-loading="dataLoading"
        element-loading-background="rgba(255, 255, 255, 0.5)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
      >
        <el-table-column prop="key" label="参数" min-width="20%">
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.key}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="40%"></el-table-column>
        <el-table-column prop="value" label="当前量" min-width="20%"></el-table-column>
      </el-table>
    </div>
    <div class="table-title">磁盘使用量</div>
    <!-- <div class="echart-box"> -->
    <!-- </div> -->
    <div class="swiper-widebox">
      <div class="swiper-container">
        <div class="swiper-wrapper">
          <div class="swiper-slide" v-for="(single,index) of gaugeData" :key="index">
            <div class="slide-single">
              <div :id="'echartGauge'+index" class="echart-pic"></div>
            </div>
          </div>
        </div>
        <div class="swiper-pagination"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { httpClient } from "@/utils/http";
import echarts from "echarts";
import Swiper from "swiper";
import "swiper/css/swiper.min.css";

var cpuLive; //redis计时器

export default {
  name: "CpuTab",
  props: ["dynamicUrl"],
  data() {
    return {
      tableData: [
        {
          key: "system.cpu.count",
          description: "CPU数量",
          value: ""
        },
        {
          key: "system.cpu.usage",
          description: "CPU负荷",
          value: ""
        },
        {
          key: "process.start.time",
          description: "应用启动时间",
          value: ""
        },
        {
          key: "process.uptime",
          description: "应用运行时长",
          value: ""
        },
        {
          key: "process.cpu.usage",
          description: "应用占用CPU",
          value: ""
        }
      ],
      gaugeData: [],
      serviceUrl: "T4Cloud-Support",
      cpuinfo: "/actuator/metrics/",
      diskInfo: "/actuator/system/diskInfo",
      hasService: true,
      dataLoading: false,
      timeStr: "",
      gaugeOptions: {
        title: {
          subtext: ""
        },
        tooltip: {
          formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {},
        series: [
          {
            type: "gauge",
            detail: { formatter: "{value}%" },
            data: [{ value: "", name: "" }]
          }
        ]
      }
    };
  },
  mounted() {},
  destroyed() {},
  methods: {
    getCPUInfo() {
      for (let i = 0; i < this.tableData.length; i++) {
        let finalUrl =
          "/" + this.serviceUrl + this.cpuinfo + this.tableData[i]["key"];
        this.dataLoading = true;
        setTimeout(() => {
          httpClient("GET", finalUrl, null).then(
            res => {
              if (res) {
                this.dataLoading = false;
                this.tableData[i]["value"] = res.measurements[0]["value"];
                //数据处理
                this.dateDispose(i, res.name);
              }
            },
            err => {
              //err处理
              this.dataLoading = false;
              let errRes = err.response;
              if (errRes.status == "404" || errRes.status == "500") {
                //未开通服务
                this.tableData = [];
              }
            }
          );
        }, 1500);
      }
    },
    getDiskInfo() {
      let finalUrl = "/" + this.serviceUrl + this.diskInfo;
      httpClient("GET", finalUrl, null).then(res => {
        if (res.success) {
          this.hasService = true;
          this.gaugeData = res.result["diskInfo"];
          //增加测试数据
          // for (let i = 0; i < 4; i++) {
          //   this.gaugeData.push(this.gaugeData[0]);
          // }
          for (let i = 0; i < this.gaugeData.length; i++) {
            this.gaugeData[i]["echartsId"] = "echartGauge" + i;
          }
          this.timeStr = this.timeTrans(res.timestamp);
          setTimeout(() => {
            this.drawEchart();
            this.initSwiper();
          }, 1000);
        }
      });
    },
    drawEchart() {
      let vm = this;
      for (let i = 0; i < this.gaugeData.length; i++) {
        vm.cpuChart = echarts.init(document.getElementById("echartGauge" + i));
        vm.cpuChart.setOption(vm.gaugeOptions);
        window.onresize = vm.cpuChart.resize;
        vm.cpuChart.showLoading({
          text: "loading...",
          maskColor: "rgba(255,255,255,0.5)",
          textColor: "#333",
          color: "teal"
        });
        let percentNum = parseFloat(this.gaugeData[i]["restPPT"]);
        let diskinfo =
          this.gaugeData[i]["rest"] +
          "GB/" +
          this.gaugeData[i]["max"] +
          "GB(已使用量/总量)";
        let diskname = this.gaugeData[i]["name"];
        setTimeout(() => {
          vm.gaugeOptions.title["subtext"] = diskinfo;
          vm.gaugeOptions.series[0]["data"][0]["value"] = percentNum;
          vm.gaugeOptions.series[0]["data"][0]["name"] = diskname;
          vm.cpuChart.setOption(vm.gaugeOptions);
          window.onresize = vm.cpuChart.resize;
          vm.cpuChart.hideLoading();
        }, 1000);
      }
    },
    //自动刷新数据
    autoUpdate() {
      this.getCPUInfo();
      this.getDiskInfo();
      // 实时信息计时30s调用接口
      cpuLive = self.setInterval(() => {
        this.getCPUInfo();
        this.getDiskInfo();
      }, 30000);
    },
    //手动刷新数据
    manualUpdate() {
      this.getCPUInfo();
      this.getDiskInfo();
    },
    stopTimer() {
      window.clearInterval(cpuLive);
    },
    initSwiper() {
      new Swiper(".swiper-container", {
        slidesPerView: 3,
        pagination: {
          el: ".swiper-pagination"
        },
        observer: true,
        observeParents: true
      });
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
    },
    //天-小时-分
    dayHourMin(time) {
      let s = parseInt(time);
      let day = Math.floor(s / (24 * 3600));
      let hour = Math.floor((s - day * 24 * 3600) / 3600);
      let minute = Math.floor((s - day * 24 * 3600 - hour * 3600) / 60);
      return day + "天" + hour + "时" + minute + "分";
    },
    //数据处理
    dateDispose(i, name) {
      switch (name) {
        case "system.cpu.count":
          this.tableData[i]["value"] = this.tableData[i]["value"] + "个";
          break;
        case "system.cpu.usage":
        case "process.cpu.usage":
          this.tableData[i]["value"] = Number(
            this.tableData[i]["value"] * 100
          ).toFixed(2)+"%";
          break;
        case "process.start.time":
          this.tableData[i]["value"] = new Date(parseInt(this.tableData[i]["value"]) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
          break;
        case "process.uptime":
          this.tableData[i]["value"] = this.dayHourMin(
            this.tableData[i]["value"]
          );
          break;
        default:
          break;
      }
    }
  },
  watch: {
    dynamicUrl: {
      deep: true,
      handler(newValue) {
        if (newValue) this.serviceUrl = newValue;
        //改变服务器触发
        this.getCPUInfo();
        this.getDiskInfo();
      }
    }
  }
};
</script>

<style lang="scss">
.echart-pic {
  width: 320px;
  height: 320px;
}
.cpu-table {
  width: 90%;
  margin: 5%;
}
.swiper-widebox {
  // overflow: hidden;
  margin: 3% 0;
}
.swiper-container {
  width: 100%;
  height: 100%;
}
.swiper-slide {
  background-position: center;
  background-size: cover;
}
.slide-single {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>