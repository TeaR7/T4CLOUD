import Vue from 'vue'
import Router from 'vue-router'
import { constantRouterMap } from './router.config'
Vue.use(Router)

export default new Router({
  mode : 'history',
  routes: constantRouterMap
  // routes: [{
  //   path: '/',
  //   name: 'Login',
  //   component: Login
  // }, {
  //   path: '/FirstPage',
  //   name: 'FirstPage',
  //   component: FirstPage,
  //   redirect: '/order',
  //   children: [{
  //     path: '/order',
  //     name: 'Order',
  //     component: Order
  //   }]
  // }]
})
