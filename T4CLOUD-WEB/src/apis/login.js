import apiAxios from '../http/http'
/**
 * 获取图片验证码
 * @returns {*}
 */
export function fetchCode(data) {
  // return apiAxios({
  //   url: '/sys/login',
  //   method: 'post',
  //   data: parameter
  // })
  return apiAxios('GET',"T4Cloud-User/login/checkCode", data)
}

/**
 * 登录
 * parameter: {
  * code: '',
  * codeKey: '',
  * password: '',
  * username: ''
  *  }
 * @returns {*}
 */
export function fetchLogin(data) {
  return apiAxios('POST',"T4Cloud-User/login/loginByPwd", data)
}
/**
 * 获取用户信息
 * @returns {*}
 */
export function fetchGetUserInfo() {
  return apiAxios('GET',"T4Cloud-User/login/userInfo", null)
}
/**
 * 修改用户密码
 * @returns {*}
 */
export function fetchChangePwd(data) {
  return apiAxios('POST',"T4Cloud-User/login/changePwd", data)
}
/**
 * 退出登录
 * @returns {*}
 */
export function fetchExitLogin() {
  return apiAxios('DELETE',"T4Cloud-User/login/logout", null)
}