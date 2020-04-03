import Vue from "vue";
import {
  ACCESS_TOKEN,
  SYS_USER_AUTH,
  USER_NAME,
  USER_INFO,
  SYS_USER_ROLE
} from "@/store/mutation-types";
import {
  fetchLogin,
  fetchExitLogin,
  fetchUserPermission,
  fetchUserRole
} from "@/apis/login";
import { Notification } from "element-ui";
const user = {
  state: {
    token: "",
    username: "",
    info: {},
    permissionList: [],
    roleList: []
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
      state.permissionList = permissionList;
    },
    SET_ROLELIST: (state, roleList) => {
      state.roleList = roleList;
    }
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
            if (response.success == true) {
              const menuData = response.result;
              commit("SET_PERMISSIONLIST", menuData);
              sessionStorage.setItem(SYS_USER_AUTH, JSON.stringify(menuData));
              resolve(response);
            } else {
              reject(response);
            }
          })
          .catch(error => {
            Notification.error({ title: "网关提示", message: "微服务：系统服务，暂不可用" });
            reject(error);
          });
      });
    },
    // 获取用户角色
    GetUserRole({ commit }) {
      return new Promise((resolve, reject) => {
        fetchUserRole()
          .then(response => {
            if (response.code == 200) {
              const roles = response.result;
              commit("SET_ROLELIST", roles);
              sessionStorage.setItem(SYS_USER_ROLE, JSON.stringify(roles));
              resolve(response);
            } else {
              reject(response);
            }
          })
          .catch(error => {
            Notification.error({ title: "网关提示", message: "微服务：系统服务，暂不可用" });
            reject(error);
          });
      });
    },
    // 退出登录
    Logout({ commit }) {
      return new Promise(resolve => {
        fetchExitLogin()
          .then(res => {
            if (res.success) {
              commit("SET_TOKEN", "");
              Vue.ls.remove(ACCESS_TOKEN);
            }
            resolve(res);
          })
          .catch(() => {
            resolve();
          });
      });
    }
  }
};
export default user;
