import Vue from "vue";
import { DEFAULT_COLOR, SYS_TIME } from "@/store/mutation-types";

const app = {
  state: {
    color: null, // 主题色
    device: 'desktop',
    reduceTimes: 0
  },
  mutations: {
    TOGGLE_COLOR: (state, color) => {
      Vue.ls.set(DEFAULT_COLOR, color)
      state.color = color
    },
    TOGGLE_DEVICE: (state, device) => {
      state.device = device
    },
    TOGGLE_TIMES: (state, reduceTimes) => {
      Vue.ls.set(SYS_TIME, reduceTimes)
      state.reduceTimes = reduceTimes
    },
  },
  actions: {
    ToggleColor({ commit }, color) {
      commit('TOGGLE_COLOR', color)
    },
    ToggleDevice({ commit }, device) {
      commit('TOGGLE_DEVICE', device)
    },
    ToggleTimes({ commit }, reduceTimes) {
      commit('TOGGLE_TIMES', reduceTimes)
    }
  }
}
export default app