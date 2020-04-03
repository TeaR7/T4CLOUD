<template>
  <div>
    <el-container class="analysisWrap" v-loading="loading">
      <el-main class="mainDiv">
        <div class="main-top">
          <div class="topDiv">
            <div class="topHead">
              <span>总用户数</span>
              <el-tooltip class="item" effect="dark" content="当前系统用户注册数量" placement="left-start">
                <i class="el-icon-warning-outline helpIcon"></i>
              </el-tooltip>
            </div>
            <div class="topMiddle">
              {{registerData.total}}
            </div>
            <div class="topFoot">
              <span>
                近<b>{{registerData.dayCount.length}}</b>天注册人数
                <b>{{registerData.total}}</b>人
                <i :class="registerData.lastRegistryTrend=='up'?'el-icon-caret-top':'el-icon-caret-bottom'"></i>  
              </span>
              <span>
                今日注册人数 <b>{{registerData.lastRegistry}}</b>人
                <i :class="registerData.todayRegistryTrend=='up'?'el-icon-caret-top':'el-icon-caret-bottom'"></i>
              </span>
            </div>
          </div>
          <div class="topDiv">
            <div class="topHead">
              <span>注册人数曲线</span>
              <el-tooltip class="item" effect="dark" :content="'最近'+registerData.dayCount.length+'天组册人数曲线'" placement="left-start">
                <i class="el-icon-warning-outline helpIcon"></i>
              </el-tooltip>
            </div>
            <div>
              <div id="registerEchartId" style="width:100%;height: 100px;"></div>
            </div>
          </div>
        </div>
        <div class="main-middle">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="访问管理" name="first">
              <div class="visitedNumDiv">
                 <div>
                   <p class="nameP">今日IP</p>
                   <p class="qtyP">{{visitedData.todayIp}}<i class="qtyIcon el-icon-location-information"></i></p>
                 </div>
                 <div>
                   <p class="nameP">今日访问</p>
                   <p class="qtyP">{{visitedData.todayVisit}}<i class="qtyIcon el-icon-user"></i></p>
                  
                 </div>
                 <div>
                   <p class="nameP">总访问量</p>
                   <p class="qtyP">{{visitedData.totalVisit}}<i class="qtyIcon el-icon-finished"></i></p>
                 </div>
              </div>
              <div id="visitedEchartId" style="width:100%;height:270px;"></div>
            </el-tab-pane>
            <!-- <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane> -->
          </el-tabs>
        </div>
      </el-main>
      <el-aside class="rightDiv" width="310px">
        <p class="titleP">工单/留言</p>
        <div class="progressDiv">
          <span>未处理:1</span>
          <span>跟进中:4</span>
          <span>已处理:20</span>
        </div>
        <div class="listDiv">
          <el-timeline>
            <el-timeline-item v-for="item in 4" :key="item" icon="el-icon-edit" size="large" color="#67C23A">
              <!-- <div class="timeItemDiv"> -->
              <el-card class="timeItemDiv">
                <div class="timeHead">
                  <span>
                    <el-tag type="success" size="mini">报名</el-tag>
                  </span>
                  <span>
                    <el-tag class="typeTag" type="danger" size="mini">app</el-tag>
                    <el-tag type="info" size="mini">2020-11-11 10:11</el-tag>
                  </span>
                </div>
                <div class="timeCon">王小虎 提交于 2018/4/12 20:46</div>
              </el-card>
              <!-- </div> -->
            </el-timeline-item>

            <!-- <el-timeline-item placement="top">
              <el-card>
                <h4>更新 Github 模板</h4>
                <p>王小虎 提交于 2018/4/2 20:46</p>
              </el-card>
            </el-timeline-item>-->
          </el-timeline>
        </div>
        <div class="footDiv"></div>
      </el-aside>
    </el-container>
  </div>
</template>
<script>
import echarts from "echarts";
export default {
  data() {
    return {
      loading: false,
      activeName: 'first',
      registerChart: null,
      visitedChart: null,
      registerData: {
        dayCount: []
      },
      visitedData: {},
      url: {
        visit: '/T4Cloud-Support/dashboard/visitInfo',
        registerNum: '/T4Cloud-User/dashboard/registryInfo'
      },
      registerOption: {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          show: false,
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          show: false,
          type: 'value'
        },
        grid: {
          top: '10%',
          left: '0%',
          right: '0%',
          bottom: '16%'
        },
        lineStyle: {
          opacity: 0
        },
        series: [{
          name: '注册量',
          smooth: true,
          symbol: 'none',
          data: [10, 15, 30, 0, 0, 0, 20, 0],
          type: 'line',
          areaStyle: {
            color: 'teal'
          }
        }]
      },
      visitedOption: {
        // title: {
        //   text: '折线图堆叠'
        // },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          bottom: 5,
          data: ['ip', 'visit']
        },
        grid: {
          top: '8%',
          left: '3%',
          right: '6%',
          bottom: '15%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              type: 'dotted'
            }
          }
        },
        series: [
          {
            name: 'ip',
            type: 'line',
            stack: '总量',
            data: [150, 232, 201, 154, 190, 330, 410]
          },
          {
            name: 'visit',
            type: 'line',
            stack: '总量',
            data: [320, 332, 301, 334, 390, 330, 320]
          }
        ]
      }
    }
  },
  created() {
    this.getVisitedList()
    this.getRegisterList()
  },
  mounted() {
    this.registerEchart = echarts.init(
      document.getElementById("registerEchartId")
    );
    this.visitedEchart = echarts.init(
      document.getElementById("visitedEchartId")
    );
    const that = this;
    window.addEventListener("resize", function () {
      that.registerEchart.resize();
      that.visitedEchart.resize();
    });
  },
  methods: {
    // 获取访问量数据
    getVisitedList() {
      this.loading = true
      this.$http.GET(this.url.visit).then(res => {
        if (res.success) {
          this.visitedData = res.result
          const list = res.result.dailyVisitInfo
          let xAxisData = []
          let seriesData1 = []
          let seriesData2 = []
          list.forEach(item => {
            xAxisData.push(item.day)
            seriesData1.push(item.ip)
            seriesData2.push(item.visit)
          });
          this.visitedOption.xAxis.data = xAxisData
          this.visitedOption.series[0].data = seriesData1
          this.visitedOption.series[1].data = seriesData2
          this.visitedEchart.setOption(this.visitedOption);
        } else {
          this.$message.warning(res.message);
        }
      }).finally(() => {
        this.loading = false
      })
    },
    // 获取访问量数据
    getRegisterList() {
      this.loading = true
      this.$http.GET(this.url.registerNum).then(res => {
        if (res.success) {
          this.registerData = res.result
          const list = res.result.dayCount
          let xAxisData = []
          let seriesData = []
          list.forEach(item => {
            xAxisData.push(item.x)
            seriesData.push(item.y)
          });
          this.registerOption.xAxis.data = xAxisData
          this.registerOption.series[0].data = seriesData
          this.registerEchart.setOption(this.registerOption);
        } else {
          this.$message.warning(res.message);
        }
      }).finally(() => {
        this.loading = false
      })
    },
    // 获取
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../styles/theme.scss";
.analysisWrap {
  background-color: #fafafa;
  .mainDiv {
    margin-right: 12px;
    padding: 0;
    .main-top {
      display: flex;
      margin-left: -12px;
      .topDiv {
        height: 155px;
        padding: 20px;
        background-color: #fff;
        margin-left: 12px;
        flex-grow: 1;
        width: 40%;
        .topHead {
          display: flex;
          align-items: center;
          justify-content: space-between;
          font-size: 13px;
          color: #777;
          .helpIcon {
            font-size: 16px;
          }
        }
        .topMiddle {
          margin-top: 10px;
          font-size: 25px;
          font-weight: 500;
        }
        .topFoot {
          margin-top: 20px;
          font-size: 13px;
          color: #777;
          .el-icon-caret-top{
            color: red;
          }
          .el-icon-caret-bottom{
            color: green;
          }
          span{
            margin-right: 15px;
            b{
              margin: 0px 3px;
              font-weight: 500;
              color: #333;
            }
          }
        }
      }
    }
    .main-middle {
      margin-top: 12px;
      background-color: #fff;
      .visitedNumDiv{
        height: 70px;
        display: flex;
        align-items: center;
        div{
          // width: 30%;
          flex-grow: 1;
          text-align: center;
          .nameP{
            font-size: 13px;
            color: #666;
          }
          .qtyP{
            margin-top: 2px;
            font-size: 23px;
            font-weight: 500;
            color: rgba($color: #000000, $alpha: 0.8);
            margin-right: -30px;
            .qtyIcon{
              margin-left: 7px;
              color: $mainColor;
            }
          }
        }
      }
    }
  }
  .rightDiv {
    background-color: #fff;
    padding: 10px 0;
    .titleP {
      text-align: center;
      font-size: 14px;
      color: #000;
      font-weight: 500;
    }
    .progressDiv {
      margin-top: 15px;
      padding-bottom: 10px;
      display: flex;
      font-size: 12px;
      span {
        text-align: center;
        width: 33%;
        flex-grow: 1;
        color: #333;
      }
      box-shadow: 0px 1px 2px #ebebeb;
    }
    .listDiv {
      margin-top: 20px;
      padding: 0px 12px;
      .timeItemDiv {
        /deep/.el-card__body {
          padding: 15px 10px;
        }
        .timeHead {
          display: flex;
          justify-content: space-between;
          .typeTag {
            margin-right: 5px;
          }
        }
        .timeCon {
          margin-top: 10px;
          font-size: 13px;
          color: #333;
        }
      }
    }
  }
}
@media screen and( max-width: 650px){
  .analysisWrap{
    flex-direction: column !important;
    .mainDiv{
      margin-right: 0px;
      .main-top{
         flex-direction: column;
         .topDiv{
           width: 100%;
           margin-bottom: 10px;
         }
      }
    }
    .rightDiv{
      margin-top: 10px;
      width: 100% !important;
    }
  }
}
</style>

