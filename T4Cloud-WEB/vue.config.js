module.exports = {
  // publicPath: './', // 打包app时放开该配置
  // 所有的接口请求代理
  // baseUrl: process.env.VUE_APP_URL,
  outputDir: "dist-admin",
  devServer: {
    port: 8000,
    open: true,
    // overlay: {
    //     warnings: false,
    //     errors: true
    // },
    proxy: {
      "/T4Cloud-User": {
        target: process.env.VUE_APP_URL,
        secure: false, // 如果是https接口，需要配置这个参数
        ws: true,
        changeOrigin: true
        // pathRewrite: {
        //     '^/api': ''
        // }
      }
    }
  },
  configureWebpack: {
    externals: {
      AMap: "AMap" // 高德地图配置
    }
  }
};
