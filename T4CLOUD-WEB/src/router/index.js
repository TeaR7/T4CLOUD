import Vue from 'vue'
import Router from 'vue-router'
import firstpage from '@/views/firstpage'
import orderManage from '@/views/orderManage'
import login from '@/views/login'
Vue.use(Router)

export default new Router({
  mode : 'history',
  routes: [{
    path: '/',
    name: 'login',
    component: login
  }, {
    path: '/firstpage',
    name: 'firstpage',
    component: firstpage,
    redirect: '/orderManage',
    children: [{
      path: '/orderManage',
      name: 'orderManage',
      component: orderManage
    }]
  }]
})
