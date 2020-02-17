import Vue from 'vue'
import Router from 'vue-router'
import FirstPage from '@/views/FirstPage'
import OrderManage from '@/views/order/OrderManage'
import Login from '@/views/user/Login'
Vue.use(Router)

export default new Router({
  mode : 'history',
  routes: [{
    path: '/',
    name: 'Login',
    component: Login
  }, {
    path: '/FirstPage',
    name: 'FirstPage',
    component: FirstPage,
    redirect: '/orderManage',
    children: [{
      path: '/orderManage',
      name: 'OrderManage',
      component: OrderManage
    }]
  }]
})
