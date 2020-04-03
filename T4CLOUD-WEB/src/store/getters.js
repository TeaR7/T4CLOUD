import Vue from 'vue'
import { USER_NAME, USER_INFO} from "@/store/mutation-types"
const getters = {
  device: state => state.app.device,
  token: state => state.user.token,
  username: state => {state.user.username = Vue.ls.get(USER_NAME); return state.user.username},
  userInfo: state => {state.user.info = Vue.ls.get(USER_INFO); return state.user.info},
  permissionList: state => state.user.permissionList,
  addRouters: state => state.permission.addRouters,
  routers: state => state.permission.routers,
  color: state => state.app.color,
  reduceTimes: state => state.app.reduceTimes
}

export default getters