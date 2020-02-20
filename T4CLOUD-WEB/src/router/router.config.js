/**
 * 动态路由
 * @type { *[] }
 */
export const asyncRouterMap = [

  {
    path: '/',
    name: 'dashboard',
    component: () => import('@/views/FirstPage'),
    meta: { title: '首页' },
    // redirect: '/dashboard/workplace',
    children: [
    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]
 
/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user/login',
    component: () => import('@/views/user/Login'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  }
]
