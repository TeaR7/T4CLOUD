import Vue from 'vue'
import { USER_NAME, USER_INFO} from "@/store/mutation-types"
const getters = {
  token: state => state.user.token,
  username: state => {state.user.username = Vue.ls.get(USER_NAME); return state.user.username},
  userInfo: state => {state.user.info = Vue.ls.get(USER_INFO); return state.user.info},
}

export default getters