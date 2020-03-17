
import Vue from 'vue';
// 获取当前的环境
// const CURRENTENV = process.env.NODE_ENV;
//是否log可见
const isShow = process.env.VUE_APP_LOG;
//log说明
let instructions = {
    URL: "",//window.location.href
    ComponentName: "",
    // FunctionName: ""
}
/**
 * 通用日志打印
 * @param {要打印的日志内容} args 
 * @param {日志标识,默认为log,warn,error,dir} logTag 
 */
function commLog(args, th, logTag = 'log') {
    // if (CURRENTENV !== 'development') return
    if (!isShow) return
    instructions.URL = window.location.href;
    if (th.$options.name) instructions.ComponentName = th.$options.name;
    let instructionsStr = JSON.stringify(instructions);
    //提纯数据
    let finalArgs = JSON.parse(JSON.stringify(args[1]));
    switch (logTag) {
        case 'log': console.log(instructionsStr) + console.table(finalArgs); break;
        case 'warn': console.log(instructionsStr) + console.table(finalArgs); break;
        case 'error': console.log(instructionsStr) + console.table(finalArgs); break;
        case 'dir': console.log(instructionsStr) + console.table(finalArgs); break;
    }
}
Vue.use({
    install: (Vue) => {
        // 普通日志
        Vue.prototype.$log = function (th) {
            commLog(arguments, th);
        };
        // 警告日志
        Vue.prototype.$warn = function (th) {
            commLog(arguments, th, 'warn');
        }
        // 错误日志
        Vue.prototype.$error = function (th) {
            commLog(arguments, th, 'error');
        }
        // 打印对象
        Vue.prototype.$dir = function (th) {
            commLog(arguments, th, 'dir');
        }
    }
})

//使用方式
//this.$log(this,logObject)