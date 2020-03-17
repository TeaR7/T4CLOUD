import { havePermission } from "@/utils/util"
const hasPermission = {
  install(Vue, options) {
    console.log("options", options);
    Vue.directive("auth", {
      inserted: (el, binding, vnode) => {
        filterGlobalPermission(0, el, binding, vnode);
      }
    });
    Vue.directive("role", {
      inserted: (el, binding, vnode) => {
        filterGlobalPermission(1, el, binding, vnode);
      }
    });
  }
};
/**
 * 全局权限控制
 */
export function filterGlobalPermission (type, el, binding){
  let isFind = havePermission(binding.value,type)
  if(isFind==false){
    el.parentNode.removeChild(el);
  }
}

export default hasPermission;
