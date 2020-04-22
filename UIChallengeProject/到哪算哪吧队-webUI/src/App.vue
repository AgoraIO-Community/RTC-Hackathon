<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
html, body, p, div, h1, h2, h3, h4, ul, ol, li, form,  button, input, textarea, th, td { margin: 0; padding: 0;font-family: 'Microsoft JhengHei';}
img { border: 0 none; vertical-align: top; }
ol, ul { list-style: none; }
input, select, button, h1, h2, h3, h4 { font-size: 100%; font-family: inherit; }
input{background: none;border: none;outline: none;}
table { border-collapse: collapse; border-spacing: 0; }
a { text-decoration: none; color: #666; }
input[type="text"], textarea { -webkit-appearance: none; -moz-appearance: none; appearance: none; }
body{
  // background: #ebbfc9;
  min-width: 750px;
}
</style>
