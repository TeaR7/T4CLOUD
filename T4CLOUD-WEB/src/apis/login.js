import http from  "t4cloud-util/lib/http";
/**
 * 获取图片验证码
 * @returns {*}
 */
export function fetchCode(data) {
    return http.GET("/T4Cloud-User/login/checkCode", data)
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
    return http.POST("/T4Cloud-User/login/loginByPwd", data)
}

/**
 * 获取用户信息
 * @returns {*}
 */
export function fetchGetUserInfo() {
    return http.GET("/T4Cloud-User/login/userInfo", null)
}

/**
 * 修改用户密码
 * @returns {*}
 */
export function fetchChangePwd(data) {
    return http.POST("/T4Cloud-User/login/changePwd", data)
}

/**
 * 退出登录
 * @returns {*}
 */
export function fetchExitLogin() {
    return http.DELETE("/T4Cloud-User/login/logout", null)
}

/**
 * 获取用户菜单权限
 * @returns {*}
 */
export function fetchUserPermission() {
    return http.GET("/T4Cloud-System/SysPermission/userPermission")
}

/**
 * 角色表-获取用户角色权限
 * @returns {*}
 */
export function fetchUserRole() {
    return http.GET("/T4Cloud-System/SysRole/userRole")
}

