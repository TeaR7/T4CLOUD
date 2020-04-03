<template>
  <div>
    <el-carousel
      type="card"
      height="100px"
      @change="carouselChange"
      ref="carousel"
      :autoplay="false"
      arrow="never"
      trigger="click"
    >
      <el-carousel-item v-for="(item,index) in serviceArrs" :key="index">
        <div class="card-text-box">
          <div>
            <div class="card-text-main">{{item.blockName}}</div>
            <div class="card-text-second">集群数量:{{item.number}}</div>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
    <el-tabs type="border-card" @tab-click="chooseTab" value="redis">
      <el-tab-pane label="Redis" name="redis">
        <RedisTab :dynamicUrl="dynamicUrl" ref="redis"></RedisTab>
      </el-tab-pane>
      <el-tab-pane label="服务器" name="cpu">
        <CpuTab :dynamicUrl="dynamicUrl" ref="cpu"></CpuTab>
      </el-tab-pane>
      <el-tab-pane label="容器信息" name="container">
        <ContainerTabModel :dynamicUrl="dynamicUrl" ref="container"></ContainerTabModel>
      </el-tab-pane>
      <el-tab-pane label="请求追踪" name="request">
        <RequestTabModel :dynamicUrl="dynamicUrl" ref="request"></RequestTabModel>
      </el-tab-pane>
      <el-tab-pane label="在线文档" name="onlineWord">
        <div v-if="dynamicUrl=='T4Cloud-Gateway'">该微服务暂未提供在线文档</div>
        <iframe
          v-else-if="recordTab=='onlineWord'"
          :src="onlineWordUrl"
          frameborder="0"
          width="100%"
          height="800px"
          scrolling="auto"
        ></iframe>
      </el-tab-pane>
      <el-tab-pane label="SQL监控" name="sqlMonitor">
        <div v-if="dynamicUrl=='T4Cloud-Gateway'">该微服务暂未提供监控</div>
        <iframe
          v-else
          :src="sqlMonitorUrl"
          frameborder="0"
          width="100%"
          height="800px"
          scrolling="auto"
        ></iframe>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
//tab组件
import RedisTab from "./modals/RedisTabModal";
import CpuTab from "./modals/CpuTabModel";
import ContainerTabModel from "./modals/ContainerTabModel";
import RequestTabModel from "./modals/RequestTabModel";

export default {
  name: "Monitor",
  components: {
    RedisTab,
    CpuTab,
    ContainerTabModel,
    RequestTabModel
  },
  data() {
    return {
      serviceArrs: [],
      diskData: "",
      dynamicUrl: "T4Cloud-Support",
      activeIndex: 0,
      url: {
        serviceList: "/T4Cloud-Support/actuator/serviceList"
      },
      recordTab: "redis",
      onlineWordUrl: "",
      sqlMonitorUrl: ""
    };
  },
  created() {
    this.setUrlPath();
    this.getServiceList();
  },
  beforeDestroy() {
    this.$refs[this.recordTab].stopTimer();
  },
  mounted() {
    //默认Redis30秒自动刷新
    this.$refs[`redis`].autoUpdate();
  },
  methods: {
    //滑块数据
    getServiceList() {
      this.$http.GET(this.url.serviceList, null).then(res => {
        if (res.success) {
          this.serviceArrs = res.result;
          //数据处理
          for (let i of this.serviceArrs) {
            i["blockName"] = i["name"].split("-")[1];
          }
        }
      });
    },
    //滑块切换
    carouselChange() {
      this.activeIndex = this.$refs.carousel.activeIndex;
      this.dynamicUrl = this.serviceArrs[this.activeIndex]["name"];
      this.setUrlPath();
    },
    //tab选中开始30秒自动刷新
    chooseTab(tab) {
      if (tab.name != this.recordTab) {
        // 在线文档 和 SQL监控 没有定时器
        if (this.recordTab != "onlineWord" && this.recordTab != "sqlMonitor") {
          this.$refs[this.recordTab].stopTimer();
        }
        if (tab.name != "onlineWord" && tab.name != "sqlMonitor") {
          this.$refs[tab.name].autoUpdate();
        }
        this.recordTab = tab.name;
      }
    },
    //在线文档 和 SQL监控 url赋值
    setUrlPath() {
      this.onlineWordUrl =
        process.env.VUE_APP_URL + "/" + this.dynamicUrl + "/doc.html";
      // console.log(this.onlineWordUrl);
      this.sqlMonitorUrl =
        process.env.VUE_APP_URL + "/" + this.dynamicUrl + "/druid/login.html";
    }
  }
};
</script>

<style lang="scss">
.el-carousel__item .card-text-box {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #475669;
  text-align: center;
}

.card-text-main {
  font-size: 18px;
}

.card-text-second {
  margin-top: 5px;
  font-size: 12px;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.table-pc {
  width: 90%;
  margin: 10px 5% 0 5%;
}

.table-mobile {
  width: 100%;
  margin: 10px 0 0 0;
}
</style>
