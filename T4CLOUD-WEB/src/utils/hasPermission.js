import { SYS_USER_AUTH } from "@/store/mutation-types"
const hasPermission = {
  install(Vue, options) {
    console.log('options', options);
    Vue.directive("authz", {
      inserted: (el, binding, vnode) => {
        filterGlobalPermission(el, binding, vnode);
      }
    });
  }
};
/**
 * 全局权限控制
 */
export function filterGlobalPermission(el, binding) {
  let allAuthList = JSON.parse(sessionStorage.getItem(SYS_USER_AUTH) || "[]");
  // console.log(allAuthList)
  const valueArr = binding.value.split(':')
  const valueStr = valueArr[0]+':'+valueArr[1]
  for (var auth of allAuthList) {
    const permsArr = auth.perms.split(':')
    const permsStr = permsArr[0]+':'+permsArr[1]
    if(permsStr == valueStr) {
      if(auth.children&&auth.children.length>0){
        auth.children.forEach(item=>{
          if(item.perms==binding.value){
            el.parentNode.removeChild(el);
            return
          }
        })
      }
    }
  }
}

export default hasPermission;
