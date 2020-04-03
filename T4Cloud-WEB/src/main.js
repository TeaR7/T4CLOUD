import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../src/styles/index.scss' // global css
import store from './store/'
import config from '@/defaultSettings'
import VueI18n from 'vue-i18n'
import Fragment from 'vue-fragment'
import '@/permission' // permission control
import Storage from 'vue-ls'
import '../src/styles/element-variables.scss'
import hasPermission from '@/utils/hasPermission'
import './utils/log'

import {ACCESS_TOKEN} from "@/store/mutation-types"

Vue.use(Storage, config.storageOptions)
Vue.use(VueI18n)
Vue.use(ElementUI, {size: 'medium'})
Vue.use(hasPermission)
Vue.use(Fragment.Plugin)


//字典注册
import Dict from '@/components/T4Cloud/TDict'
const TDict = {
    install: function (Vue) {
        Vue.component('TDict',Dict);
    }
}
Vue.use(TDict)

const i18n = new VueI18n({
    locale: 'zh-CN', // 语言表示，通过切换local来切换本地语言
    messages: {
        'en-US': require('./assets/lang/en-US'),  // 中文语言包
        'zh-CN': require('./assets/lang/zh-CN')   // 英文语言包
    }
})

Vue.config.productionTip = false

new Vue({
    el: '#app',
    i18n,
    router,
    store,
    mounted() {
        store.commit('SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))
    },
    render: h => h(App)
})

