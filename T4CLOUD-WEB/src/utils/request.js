import Vue from "vue";
import axios from "axios";
// import store from "@/store";
import { MessageBox, Notification } from "element-ui";
import { ACCESS_TOKEN } from "@/store/mutation-types";

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_URL, // api base_url
  timeout: 10000 // 请求超时时间
});

// request interceptor
service.interceptors.request.use(
  config => {
    const token = Vue.ls.get(ACCESS_TOKEN);
    if (token) {
      config.headers["T-ACCESS-TOKEN"] = token; // 让每个请求携带自定义 token 请根据实际情况自行修改
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    if (error.response) {
      let data = error.response.data;
      const token = Vue.ls.get(ACCESS_TOKEN);
      const status = error.response.status;
      console.log("------异常响应------", status);
      switch (status) {
        case 500:
          if (token && data.message.indexOf("Token失效，请重新登录") >= 0) {
            if (error.response.config.url.indexOf("login/logout") >= 0) {
              Vue.ls.remove(ACCESS_TOKEN);
              window.location.reload();
            } else {
              MessageBox.alert("很抱歉，登录已过期，请重新登录", "登录已过期", {
                confirmButtonText: "确定",
                callback: () => {
                  Vue.ls.remove(ACCESS_TOKEN);
                  window.location.reload();
                }
              });
            }
          }
          break;
        case 404:
          Notification.error({
            message: "系统提示",
            description: "很抱歉，资源未找到!",
            duration: 4
          });
          break;
        case 504:
          Notification.error({ message: "系统提示", description: "网络超时" });
          break;
        default:
          Notification.error({
            message: "系统提示",
            description: data.message,
            duration: 4
          });
          break;
      }
    }
    return Promise.reject(error);
  }
);

export { service as axios };
