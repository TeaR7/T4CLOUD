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
    <div class="table-title">JVM信息</div>
    <div :class="device=='mobile' ? 'table-mobile':'table-pc'">
      <el-table
        :data="JVMData"
        v-loading="dataLoading"
        element-loading-background="rgba(255, 255, 255, 0.5)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
      >
        <el-table-column prop="key" label="参数" min-width="40%">
          <template slot-scope="scope">
            <el-tag
              size="medium"
              :style="{'color':scope.row.darkColor,'border-color':scope.row.darkColor,'background-color':scope.row.lightColor}"
            >{{ scope.row.key}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="30%"></el-table-column>
        <el-table-column prop="value" label="当前值" min-width="30%"></el-table-column>
      </el-table>
    </div>
    <div class="table-title">Tomcat信息</div>
    <div :class="device=='mobile' ? 'table-mobile':'table-pc'">
      <el-table
        :data="tomcatData"
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
        <el-table-column prop="value" label="当前值" min-width="30%"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

var infoLive; //计时器

export default {
  name: "ContainerTab",
  props: ["dynamicUrl"],
  computed: {
    ...mapGetters(["device"])
  },
  data() {
    return {
      //请求参数
      JVMParams: [
        "java.runtime.name",
        "java.version",
        "java.home",
        "jvm.memory.max",
        "jvm.memory.committed",
        "jvm.memory.used",
        "jvm.threads.daemon",
        "jvm.threads.live",
        "jvm.threads.peak",
        "jvm.gc.pause",
        "jvm.gc.pause"
      ],
      JVMData: [
        {
          key: "java.runtime.name",
          description: "运行环境",
          value: ""
        },
        {
          key: "java.version",
          description: "环境版本",
          value: ""
        },
        {
          key: "java.home",
          description: "jdk路径",
          value: ""
        },
        {
          key: "jvm.memory.max",
          description: "JVM 最大内存",
          value: ""
        },
        {
          key: "jvm.memory.committed",
          description: "JVM 可用内存",
          value: ""
        },
        {
          key: "jvm.memory.used",
          description: "已用内存",
          value: ""
        },
        {
          key: "jvm.threads.daemon",
          description: "JVM 守护线程数量",
          value: ""
        },
        {
          key: "jvm.threads.live",
          description: "JVM 当前活跃线程数量",
          value: ""
        },
        {
          key: "jvm.threads.peak",
          description: "JVM 峰值线程数量",
          value: ""
        },
        {
          key: "jvm.gc.pause.count",
          description: "系统启动以来GC 次数",
          value: ""
        },
        {
          key: "jvm.gc.pause.totalTime",
          description: "系统启动以来GC 总耗时",
          value: ""
        }
      ],
      tomcatParams: [
        "tomcat.global.request.max",
        "tomcat.global.request",
        "tomcat.global.request",
        "tomcat.global.error",
        "tomcat.threads.current",
        "tomcat.threads.config.max"
      ],
      tomcatData: [
        {
          key: "tomcat.global.request.max",
          description: "request 请求最长耗时",
          value: ""
        },
        {
          key: "tomcat.global.request.count",
          description: "全局 request 请求次数",
          value: ""
        },
        {
          key: "tomcat.global.request.totalTime",
          description: "全局 request 请求总耗时",
          value: ""
        },
        {
          key: "tomcat.global.error",
          description: "异常请求数",
          value: ""
        },
        {
          key: "tomcat.threads.current",
          description: "tomcat 当前线程数",
          value: ""
        },
        {
          key: "tomcat.threads.config.max",
          description: "tomcat 配置的线程最大数",
          value: ""
        }
      ],
      serviceUrl: "T4Cloud-Support",
      containerinfo: "/actuator/metrics/",
      javaUrl: "/actuator/env/",
      hasService: true,
      dataLoading: false,
      timeStr: ""
    };
  },
  methods: {
    getJVMInfo() {
      let y = 0,
        centerUrl;
      for (let i = 0; i < this.JVMData.length; i++) {
        if (this.JVMParams[i].indexOf("java") != -1) {
          centerUrl = this.javaUrl;
        } else {
          centerUrl = this.containerinfo;
        }
        let finalUrl = "/" + this.serviceUrl + centerUrl + this.JVMParams[i];
        this.dataLoading = true;
        setTimeout(() => {
          this.$http.GET(finalUrl, null).then(
            res => {
              if (res) {
                this.dataLoading = false;
                //jvm.gc.pause处理
                if (!res.name) {
                  this.JVMData[i]["value"] = res.property.value;
                } else if (res.name == "jvm.gc.pause") {
                  if (y > 0) {
                    this.JVMData[i]["value"] =
                      res.measurements[y]["value"] + "秒";
                    y = 0;
                  } else {
                    this.JVMData[i]["value"] =
                      res.measurements[y]["value"] + "次";
                    y = y + 1;
                  }
                } else if (res.name.indexOf("jvm.memory") != -1) {
                  this.JVMData[i]["value"] = this.sizeTrans(
                    res.measurements[0]["value"]
                  );
                } else {
                  this.JVMData[i]["value"] = res.measurements[0]["value"];
                }
                //时间戳处理
                if (!this.timeStr) {
                  this.timeStr = this.timeTrans(new Date());
                }
              }
            },
            err => {
              //err处理
              this.dataLoading = false;
              let errRes = err.response;
              if (errRes.status == "404" || errRes.status == "500") {
                //未开通服务
                this.JVMData[i]["value"] = "暂无数据";
              }
            }
          );
        }, 1500);
      }
    },
    getTomcatInfo() {
      let y = 0;
      for (let i = 0; i < this.tomcatData.length; i++) {
        let finalUrl =
          "/" + this.serviceUrl + this.containerinfo + this.tomcatParams[i];
        this.dataLoading = true;
        setTimeout(() => {
          this.$http.GET(finalUrl, null).then(
            res => {
              if (res) {
                this.dataLoading = false;
                //tomcat.global.request处理
                if (res.name == "tomcat.global.request") {
                  if (y > 0) {
                    this.tomcatData[i]["value"] =
                      res.measurements[y]["value"] + "秒";
                    y = 0;
                  } else {
                    this.tomcatData[i]["value"] =
                      res.measurements[y]["value"] + "次";
                    y = y + 1;
                  }
                } else if (res.name == "tomcat.global.request.max") {
                  this.tomcatData[i]["value"] =
                    res.measurements[0]["value"] + "秒";
                } else {
                  this.tomcatData[i]["value"] = res.measurements[0]["value"];
                }
              }
            },
            err => {
              //err处理
              this.dataLoading = false;
              let errRes = err.response;
              if (errRes.status == "404" || errRes.status == "500") {
                //未开通服务
                this.tomcatData[i]["value"] = "暂无数据";
              }
            }
          );
        }, 1500);
      }
    },
    //自动刷新数据
    autoUpdate() {
      this.getJVMInfo();
      this.getTomcatInfo();
      this.timeStr = this.timeTrans(new Date());
      // 实时信息计时30s调用接口
      infoLive = self.setInterval(() => {
        this.getJVMInfo();
        this.getTomcatInfo();
        this.timeStr = this.timeTrans(new Date());
      }, 30000);
    },
    //手动刷新数据
    manualUpdate() {
      this.getJVMInfo();
      this.getTomcatInfo();
      this.timeStr = this.timeTrans(new Date());
    },
    stopTimer() {
      window.clearInterval(infoLive);
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
    //字节转换
    sizeTrans(size) {
      let num = 1024.0;
      return (size / Math.pow(num, 2)).toFixed(2) + "M"; //M
    },
    //颜色处理
    themeColor(i, name) {
      if (name.indexOf("jvm.memory") != -1) {
        this.JVMData[i]["darkColor"] = "#6c5ce7";
        this.JVMData[i]["lightColor"] = "#BEB6E7";
      } else if (name.indexOf("jvm.threads") != -1) {
        this.JVMData[i]["darkColor"] = "#00b894";
        this.JVMData[i]["lightColor"] = "#A7E2BF";
      } else if (name.indexOf("gc.pause") != -1) {
        this.JVMData[i]["darkColor"] = "#0984e3";
        this.JVMData[i]["lightColor"] = "##90AADA";
      } else if (name.indexOf("tomcat.global") != -1) {
        this.tomcatData[i]["darkColor"] = "#6c5ce7";
        this.tomcatData[i]["lightColor"] = "#BEB6E7";
      } else if (name.indexOf("tomcat.threads") != -1) {
        this.tomcatData[i]["darkColor"] = "#e84393";
        this.tomcatData[i]["lightColor"] = "#DE9CD4";
      }
    }
  },
  watch: {
    dynamicUrl: {
      deep: true,
      handler(newValue) {
        if (newValue) this.serviceUrl = newValue;
        //改变服务器触发
        this.getJVMInfo();
        this.getTomcatInfo();
      }
    }
  }
};
</script>

<style lang="scss">
.table-time {
  display: inline;
}
</style>