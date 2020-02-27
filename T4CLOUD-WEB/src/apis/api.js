import { GET } from '../utils/http'
//权限管理
const getPermissionList = (params)=>GET("/T4Cloud-System/SysPermission/tree",params);

export { getPermissionList }