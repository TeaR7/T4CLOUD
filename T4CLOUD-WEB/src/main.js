import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../src/styles/index.scss' // global css
import store from './store/'
import config from '@/defaultSettings'
import Storage from 'vue-ls'
Vue.use(Storage, config.storageOptions)
Vue.use(ElementUI)
import { ACCESS_TOKEN } from "@/store/mutation-types"
// import axios from 'axios';
// import http from './http/http';
// axios 拦截器
import './http/axios'

// 对后端接口 进行全局注册
// Vue.prototype.$api = api;
// 对请求方式 进行全局注册
// Vue.prototype.$http = http;
//将axios挂载在vue原型链上
// Vue.prototype.$axios = axios;
// axios.defaults.baseURL = process.env.VUE_APP_URL;


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  mounted(){
    store.commit('SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))
  },
  render: h => h(App)
})

// new Vue({
//   render: h => h(App),
// }).$mount('#app')
