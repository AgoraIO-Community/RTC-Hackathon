module.exports = {
  css: {
    loaderOptions: {
      sass: {
        prependData: `@import "~@/assets/scss/mixins/mixins.scss";`
      }
    }
  },
  publicPath: './',
  outputDir: 'dist',
  assetsDir: 'static'
}
