import Vue from "vue";
import { ACCESS_TOKEN, USER_NAME, USER_INFO } from "@/store/mutation-types";
import { fetchLogin, fetchExitLogin, fetchUserPermission } from "@/apis/login";
const user = {
  state: {
    token: "",
    username: "",
    info: {},
    permissionList: []
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
    },
    SET_PERMISSIONLIST: (state, permissionList) => {
      state.permissionList = permissionList
    },
  },
  actions: {
    // 登录
    Login({ commit }, data) {
      return new Promise((resolve, reject) => {
        fetchLogin(data)
          .then(response => {
            if (response.code == 200) {
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
    // 获取用户菜单权限
    GetUserPermission({ commit }) {
      return new Promise((resolve, reject) => {
        fetchUserPermission()
          .then(response => {
            if (response.code == 200) {
              const menuData = response.result;
              commit('SET_PERMISSIONLIST', menuData)
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
    Logout({ commit }) {
      return new Promise(resolve => {
        fetchExitLogin()
          .then(response => {
            if (response.code == 200) {
              commit("SET_TOKEN", "");
              Vue.ls.remove(ACCESS_TOKEN);
            }
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
