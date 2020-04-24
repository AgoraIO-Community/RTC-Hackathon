// vue.config.js
// module.exports = {
//   publicPath: '../',
//   assetsDir:'../',
//   baseUrl:'../',
// }


module.exports = {
  chainWebpack: config => {
    config.module
      .rule('svg-sprite')
      .use('svgo-loader')
      .loader('svgo-loader')
  }
}
