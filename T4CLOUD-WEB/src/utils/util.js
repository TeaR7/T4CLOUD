import { SYS_USER_ROLE, SYS_USER_ALLPERMISSION } from "@/store/mutation-types";
// import router from '../router'
var allBtnPermissArr = []
// 自定义判断元素类型JS
export function toType(obj) {
  return {}.toString
    .call(obj)
    .match(/\s([a-zA-Z]+)/)[1]
    .toLowerCase();
}

// 参数过滤函数
export function filterNull(o) {
  for (var key in o) {
    if (o[key] === null) {
      delete o[key];
    }
    if (toType(o[key]) === "string") {
      o[key] = o[key].trim();
    } else if (toType(o[key]) === "object") {
      o[key] = filterNull(o[key]);
    } else if (toType(o[key]) === "array") {
      o[key] = filterNull(o[key]);
    }
  }
  return o;
}

// 表单对象去重
export function removeSame(form, total) {
  let keys = Object.keys(form);
  keys.forEach(item => {
    if (total[item] != null) {
      form[item] = total[item];
    }
  });
  // console.log("form",form)
  // console.log("total",total)
  return form;
}

// 生成首页路由
export function generateIndexRoute(data) {
  allBtnPermissArr = []
  let indexRouter = [
    {
      path: "/",
      name: "dashboard",
      component: () => import("@/views/FirstPage"),
      meta: { title: "首页" },
      redirect: "/dashboard/analysis",
      children: [...generateChildRouters(data)]
    },
    {
      path: "*",
      redirect: "/404",
      hidden: true
    }
  ];
  getAllBtnPermission(data)
  sessionStorage.setItem(SYS_USER_ALLPERMISSION, JSON.stringify(allBtnPermissArr))
  return indexRouter;
}

// 生成嵌套路由（子路由）
function generateChildRouters(data) {
  const routers = [];
  for (var item of data) {
    if (item.hidden == 0 || item.status == 0 || item.menuType == 2) {
      // 隐藏路由: 0-隐藏,1-展示 // 按钮权限状态(0无效1有效)// 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
      continue;
    }

    let component = "";
    if (item.component && item.component.indexOf("layouts") >= 0) {
      component = "components/" + item.component;
    } else {
      if (item.component == "" || item.component == null) {
        component = "components/Layout/TRouteView";
      } else {
        component = "views/" + item.component;
      }
    }

    // eslint-disable-next-line
    // let URL = (item.meta.url|| '').replace(/{{([^}}]+)?}}/g, (s1, s2) => eval(s2)) // URL支持{{ window.xxx }}占位符变量
    // if (isURL(URL)) {
    //   item.meta.url = URL;
    // }
    let menu = {
      path: item.url ? item.url : "/",
      name: item.name,
      icon: item.icon,
      // redirect:item.redirect,
      component: resolve => {
        if (matchVueFiles(component) >= 0) {
          require(["@/" + component + ".vue"], resolve);
        } else {
          require(["@/" + "views/404" + ".vue"], resolve);
        }
      },
      hidden: item.hidden
      // meta: {
      //   childBtnPermisList: [item.perms],
      // }

    };
    if (item.alwaysShow) {
      menu.alwaysShow = true;
      menu.redirect = menu.path;
    }
    if (item.children && item.children.length > 0) {
      const array = [...generateChildRouters(item.children)];
      // menu.meta.childBtnPermisList = array[1]
      if (array.length > 0) {
        menu.children = [...array];
      }
    }
    routers.push(menu);
  }
  return routers;
}
function getAllBtnPermission(data){
  for (var item of data) {
    // 收集所有按钮权限
    if(item.status!==0&&item.perms!=''&&item.perms!=null){
      allBtnPermissArr.push(item.perms)
    }
    if (item.children && item.children.length > 0) {
      getAllBtnPermission(item.children)
    }
  }
}

// 看看是否有权限
// type 0 权限 1 角色
export function havePermission(permission, type) {
  let isFind = false;
  if (toType(permission) === "string") {
    isFind = type == 0 ? filterPermission(permission) : filterRole(permission);
  } else if (toType(permission) === "array") {
    for (var item of permission) {
      isFind = type == 0 ? filterPermission(item) : filterRole(item);
      if (isFind) {
        break;
      }
    }
  }
  return isFind;
}

function filterPermission(authStr) {
  if (authStr == "") {
    return true;
  }
  // let isFind = false;
  // const authList = router.history.current.meta.childBtnPermisList
  // outPoint: for (var auth of authList) {
  //   if(authStr==auth){
  //     isFind = true;
  //     break outPoint;
  //   }
  // }
  return allBtnPermissArr.indexOf(authStr)>=0;
}

function filterRole(roleCode) {
  let allRoleList = JSON.parse(sessionStorage.getItem(SYS_USER_ROLE) || "[]");
  if (roleCode == "") {
    return true;
  }
  let isFind = false;
  outPoint: for (var role of allRoleList) {
    if (roleCode == role.roleCode) {
      isFind = true;
      break outPoint;
    }
  }
  return isFind;
}
//
function matchVueFiles(name) {
  var arr = name.split("views/");
  if (arr.length > 1) {
    name = name.replace("views/", "./") + ".vue";
    const context = require.context("@/views", true, /\.vue$/);
    const keys = context.keys();
    return keys.indexOf(name);
  }
  return 0;
}
