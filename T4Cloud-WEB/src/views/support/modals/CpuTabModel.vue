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
    <div class="table-title">服务器配置</div>
    <div :class="device=='mobile' ? 'table-mobile':'table-pc'">
      <el-table
        :data="tableData"
        v-loading="dataLoading"
        element-loading-background="rgba(255, 255, 255, 0.5)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
      >
        <el-table-column prop="key" label="参数" min-width="40%">
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.key}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="30%"></el-table-column>
        <el-table-column prop="value" label="当前量" min-width="30%"></el-table-column>
      </el-table>
    </div>
    <div class="table-title">磁盘使用量</div>
    <div class="swiper-widebox" v-if="gaugeData.length>0">
      <t-swiper ref="swiper">
        <div
          v-for="(single,index) of gaugeData"
          :key="index"
          class="swiper-slide"
          slot="swiper-slot"
        >
          <div class="slide-single">
            <div :id="'echartGauge'+index" class="echart-pic"></div>
          </div>
        </div>
      </t-swiper>
    </div>
    <div class="nodata-box" v-else>
      <div class="nodata-text">暂不提供磁盘监控</div>
    </div>
  </div>
</template>

<script>
import echarts from "echarts";
import { mapGetters } from "vuex";
// import TSwiper from "@/components/T4Cloud/TSwiper";

var cpuLive; //redis计时器
var echartObj = {};

export default {
  name: "CpuTab",
  // components: {
  //   TSwiper
  // },
  props: ["dynamicUrl"],
  computed: {
    ...mapGetters(["device"])
  },
  data() {
    return {
      tableData: [
        {
          key: "os.name",
          description: "系统名称",
          value: ""
        },
        {
          key: "os.version",
          description: "系统版本号",
          value: ""
        },
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
      osinfo: "/actuator/env/",
      diskInfo: "/actuator/system/diskInfo",
      hasService: true,
      dataLoading: false,
      timeStr: "",
      gaugeOptions: {
        title: {
          subtext: ""
        },
        // tooltip: {
        //   formatter: "{a} <br/>{b} : {c}%"
        // },
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
  methods: {
    getCPUInfo() {
      let centerUrl;
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i]["key"].indexOf("os") != -1) {
          centerUrl = this.osinfo;
        } else {
          centerUrl = this.cpuinfo;
        }
        let finalUrl =
          "/" + this.serviceUrl + centerUrl + this.tableData[i]["key"];
        this.dataLoading = true;
        setTimeout(() => {
          this.$http.GET(finalUrl, null).then(
            res => {
              if (res) {
                this.dataLoading = false;
                if (!res.name) {
                  this.tableData[i]["value"] = res.property.value;
                } else {
                  this.tableData[i]["value"] = res.measurements[0]["value"];
                }
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
      this.$http.GET(finalUrl, null).then(
        res => {
          if (res.success) {
            this.hasService = true;
            this.gaugeData = res.result["diskInfo"];
            for (let i = 0; i < this.gaugeData.length; i++) {
              this.gaugeData[i]["echartsId"] = "echartGauge" + i;
            }
            this.timeStr = this.timeTrans(res.timestamp);
            setTimeout(() => {
              this.drawEcharts();
              this.$refs.swiper.initSwiper();
            }, 1000);
          }
        },
        err => {
          //err处理
          let errRes = err.response;
          if (errRes.status) {
            //未开通服务
            this.hasService = false;
            this.gaugeData = [];
          }
          this.drawEcharts();
        }
      );
    },
    drawEcharts() {
      let vm = this;
      for (let i = 0; i < this.gaugeData.length; i++) {
        vm.cpuChart = echarts.init(document.getElementById("echartGauge" + i));
        echartObj[i] = vm.cpuChart;
        echartObj[i].setOption(vm.gaugeOptions);
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
          echartObj[i].setOption(vm.gaugeOptions);
          window.onresize = vm.cpuChart.resize;
          echartObj[i].hideLoading();
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
          this.tableData[i]["value"] =
            Number(this.tableData[i]["value"] * 100).toFixed(2) + "%";
          break;
        case "process.start.time":
          this.tableData[i]["value"] = new Date(
            parseInt(this.tableData[i]["value"]) * 1000
          )
            .toLocaleString()
            .replace(/:\d{1,2}$/, " ");
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
.swiper-widebox {
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
.table-time {
  display: inline;
}
.nodata-box {
  height: 320px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.nodata-text {
  color: #666;
  font-size: 18px;
}
</style>