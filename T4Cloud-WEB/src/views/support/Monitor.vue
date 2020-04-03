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
    </el-tabs>
  </div>
</template>

<script>
import { httpClient } from "@/utils/http";
import RedisTab from "./modals/RedisTabModal";
import CpuTab from "./modals/CpuTabModel";

export default {
  name: "Monitor",
  components: {
    RedisTab,
    CpuTab
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
      recordTab: "redis"
    };
  },
  created() {
    this.getServiceList();
  },
  mounted() {
    //默认Redis30秒自动刷新
    this.$refs[`redis`].autoUpdate();
  },
  methods: {
    //滑块数据
    getServiceList() {
      httpClient("GET", this.url.serviceList, null).then(res => {
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
    },
    //tab选中开始30秒自动刷新
    chooseTab(tab) {
      if (tab.name != this.recordTab) {
        this.$refs[this.recordTab].stopTimer();
        this.$refs[tab.name].autoUpdate();
        this.recordTab = tab.name;
      }
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
</style>
