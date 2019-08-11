<template>
  <router-view v-if="isRouterAlive"/>
</template>

<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { Mutation, State, Action } from 'vuex-class'
  import publicMethods from './plugins/publicMethods'

  @Component({
    components: {},
    provide () {
      return {
        reload: this.reload,
        setParames: publicMethods.setParames,
        getParames: publicMethods.getParames,
        beforeGetData: publicMethods.beforeGetData,
        afterGetData: publicMethods.afterGetData,
        beforeSubmit: publicMethods.beforeSubmit,
        submitSucess: publicMethods.submitSucess,
        interruptJump: publicMethods.interruptJump
      }
    }
  })
  export default class App extends Vue {
    @State('theme')
    theme

    @Mutation('switchTheme')
    switchTheme

    @Action('loadMenu')
    loadMenu

    isRouterAlive = true
    /**
     * 重新加载页面
     * */
    reload () {
      this.isRouterAlive = false
      this.$nextTick(() => {
        this.isRouterAlive = true
      })
    }

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
  #app {
    background-color: rgba(255, 255, 255, .9);
    height: 100%;
  }
</style>
