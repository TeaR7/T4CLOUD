import { GET } from '../utils/http'
//字典加载
const loadDictValue = (params)=>GET("/T4Cloud-System/SysDict/dictValue/" + params);

export { loadDictValue }
