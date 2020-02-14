import Vue from "vue";
import { ACCESS_TOKEN, USER_NAME, USER_INFO } from "@/store/mutation-types";
import { fetchLogin, fetchExitLogin } from "@/apis/login";
const user = {
  state: {
    token: "",
    username: "",
    info: {}
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_NAME: (state, username) => {
      state.username = username;
    },
    SET_INFO: (state, info) => {
      state.info = info;
    }
  },
  actions: {
    // 登录
    Login({ commit }, data) {
      return new Promise((resolve, reject) => {
        fetchLogin(data)
          .then(response => {
            if (response.code == "200") {
              const userInfo = response.result;
              Vue.ls.set(ACCESS_TOKEN, userInfo.token, 7 * 24 * 60 * 60 * 1000);
              Vue.ls.set(USER_NAME, userInfo.username, 7 * 24 * 60 * 60 * 1000);
              Vue.ls.set(USER_INFO, userInfo, 7 * 24 * 60 * 60 * 1000);
              commit("SET_TOKEN", userInfo.token);
              commit("SET_INFO", userInfo);
              commit("SET_NAME", userInfo.username);
              resolve(response);
            } else {
              reject(response);
            }
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    // 退出登录
    Logout({ commit, state }) {
      return new Promise(resolve => {
        let logoutToken = state.token;
        commit("SET_TOKEN", "");
        Vue.ls.remove(ACCESS_TOKEN);
        fetchExitLogin(logoutToken)
          .then(response => {
            resolve(response);
          })
          .catch(() => {
            resolve();
          });
      });
    }
  }
};
export default user;
