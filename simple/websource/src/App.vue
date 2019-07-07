<template>
  <router-view/>
</template>

<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { Mutation, State, Action } from 'vuex-class'

  @Component({
    components: {
      // 注册两个主题组件
      leftThemeTemplate: () => import('./components/theme/left-main/Leftmain'),
      topThemeTemplate: () => import('./components/theme/top-main/Index')
    }
  })
  export default class App extends Vue {
    @State('theme')
    theme

    @Mutation('switchTheme')
    switchTheme

    @Action('loadMenu')
    loadMenu

    created () {
      this.loadMenu()
    }

    mounted () {
      console.log('The App component mounted')
    }
  }
</script>
<style lang="less">
  @import "./style/themes/default.less";
</style>
