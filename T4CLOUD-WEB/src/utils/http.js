import { axios } from "@/utils/request";
import { filterNull } from "@/utils/util";

//GET请求
export function GET(url, parameter) {
  return httpClient("GET", url, parameter);
}

//PUT请求
export function PUT(url, parameter) {
  return httpClient("PUT", url, parameter);
}

//POST请求
export function POST(url, parameter) {
  return httpClient("POST", url, parameter);
}

//DELETE请求
export function DELETE(url, parameter) {
  return httpClient("DELETE", url, parameter);
}

//通用HTTP方法，不推荐使用
export function httpClient(method, url, parameter) {
  //过滤对象中的空属性
  if (parameter) {
    parameter = filterNull(parameter);
  }
  return axios({
    url: url,
    method: method,
    data: method === "POST" || method === "PUT" ? parameter : null,
    params: method === "GET" || method === "DELETE" ? parameter : null
  });
}

/**
 * 下载文件使用，接受文件流
 */
export function download(url, parameter) {
  return axios({
    url: url,
    params: parameter,
    method: "GET",
    responseType: "blob"
  });
}
/**
 * 上传文件使用，接受文件流
 */
export function upload(url, parameter) {
  return axios({
    url: url,
    headers: {
      "Content-Type": "multipart/form-data"
    },
    data: parameter,
    method: "PUT"
  });
}
