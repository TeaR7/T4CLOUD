import Vue from "vue";
import { DEFAULT_COLOR } from "@/store/mutation-types";

const app = {
  state: {
    color: null, // 主题色
    device: 'desktop'
  },
  mutations: {
    TOGGLE_COLOR: (state, color) => {
      Vue.ls.set(DEFAULT_COLOR, color)
      state.color = color
    },
    TOGGLE_DEVICE: (state, device) => {
      state.device = device
    },
  },
  actions: {
    ToggleColor({ commit }, color) {
      commit('TOGGLE_COLOR', color)
    },
    ToggleDevice({ commit }, device) {
      commit('TOGGLE_DEVICE', device)
    },
  }
}
export default app