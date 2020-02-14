/* eslint-disable */
// 配置API接口地址
// var root = "https://cloud.t4cloud.com/";
// 引用axios
var axios = require("axios");
// 自定义判断元素类型JS
function toType(obj) {
  return {}.toString
    .call(obj)
    .match(/\s([a-zA-Z]+)/)[1]
    .toLowerCase();
}
// 参数过滤函数
function filterNull(o) {
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

function apiAxios(method, url, params, token) {
  if (params) {
    params = filterNull(params);
  }
  return axios({
    method: method,
    url: url,
    data: method === "POST" || method === "PUT" ? params : null,
    params: method === "GET" || method === "DELETE" ? params : null,
    headers: {
      'T-ACCESS-TOKEN': token?token:''
    },
    baseURL: process.env.VUE_APP_URL,
    withCredentials: false
  })
    // .then(function(res) {
    //   if (res.status === 200 || res.status === 201) {
    //     return res.data
    //   }
     
    // })
    // .catch(function(err) {
    //   return Promise.reject(error);
      // let res = err.response
      // console.log(res)
      // if (res.status == 401) {
      //   alert('授权失败,' + res.data.data)
      // } else if (res.status == 500) {
      //   alert('服务器内部错误,' + res.data.data)
      // } else if (res.status == 400) {
      //   alert('参数错误,' + res.data.data)
      // } else {
      //   alert(res.status)
      // }
      //   let res = err.response
      //   if (err) {
      //     window.alert('api error, HTTP CODE: ' + res.status)
      //   }
    // });
}
export default apiAxios;
// 返回在vue模板中的调用接口
// export default {
//   get: function(url, params, success, failure) {
//     return apiAxios("GET", url, params, success, failure);
//   },
//   post: function(url, params, success, failure) {
//     return apiAxios("POST", url, params, success, failure);
//   },
//   put: function(url, params, success, failure) {
//     return apiAxios("PUT", url, params, success, failure);
//   },
//   delete: function(url, params, success, failure) {
//     return apiAxios("DELETE", url, params, success, failure);
//   }
// };
