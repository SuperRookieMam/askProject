<template>
  <el-container class="leftc">
    <el-header>
      <el-row class="title-wrapper">
        <el-col :span="8">
          <el-row>
            <el-col>
              <router-link to="/" class="title">后台管理</router-link>
              <el-switch
                v-model="isCollapse"
                active-text="展开"
                inactive-text="收起"/>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="16">
          <el-menu mode="horizontal" background-color="#1D2023">
            <el-menu-item index="0"
                          style="padding: 0 15px;"
                          class="avatar">
              <img src="../../../assets/logo.png" alt="">
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">{{ currentUser.username }}</template>
              <!-- <el-menu-item @click="doLogout" index="2-1" style="color: white">退出</el-menu-item>-->
            </el-submenu>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-container class="leftc">
      <el-menu
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        :collapse="isCollapse"
        text-color="#FFF"
        background-color="#1D2023">
        <menu-item v-for="(menu,index) in menus"
                   :key="index"
                   :item="menu"/>
      </el-menu>
      <el-main class="elman">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import { Action, Getter } from 'vuex-class'
  import MenuItem from '../MenuItem'
  import TableBase from '../../../plugins/TableBase'

  @Component({
    components: {
      MenuItem
    }
  })
  export default class Leftmain extends Mixins(TableBase) {
    currentHtml = 'leftmain'

    isCollapse = true

    @Getter('user')
    currentUser

    @Action('loadMenu')
    loadMenu

    @Getter('menus')
    menus

    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    }

    handleClose (key, keyPath) {
      console.log(key, keyPath)
    }
    getMenus () {
      this.loadMenu({url: this.geturl(this.serverUrl.ask.menus)})
    }

    created () {
      this.getMenus()
    }
  }
</script>
<style lang="less">
  * {
    margin: 0;
    padding: 0;
  }

  html, body, .leftc {
    width: 100%;
    height: 100%;
  }

  body {
    margin: 0;
  }

  .left-template.el-aside {
    background: #1D2023;
    width: 150px !important;
  .el-menu-item {
    /*text-align: center;*/
  }
  .el-menu-item:hover, .el-submenu__title:hover {
    background: #85bdfb !important;
  }
  .el-menu {
  .el-submenu__title {
    height: 50px;
    line-height: 50px;
  }
  }
  }
  .el-header {
  .el-submenu__title {
    font-size: 18px;
    color: white !important;
  }
  .el-menu-item:hover, .el-submenu__title:hover {
    background: rgb(29, 32, 35) !important;
  }
  }
  .my-submenu {
  .el-menu--popup {
    margin-left: 0 !important;
  }
  .el-menu {
  .el-menu-item {
    height: 50px;
    /*text-align: center;*/
    min-width: 150px;
  }
  .el-menu-item:hover {
    background: #85bdfb !important;
  }
  }
  }

  .el-menu--collapse {
    width: unset;
  }
  .el-header {
    background: #1D2023;
  }
  .title-wrapper {
    width: 100%;
    height: 100%;
  .el-col {
    width: 50%;
    height: 100%;
  .title {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    font-size: 26px;
    padding-left: 30px;
    color: #fff;
  }
  }
  }

  .el-menu--horizontal {
    display: flex;
    justify-content: flex-end;
  }
  .avatar {
  img {
    display: block;
    width: 50px;
    height: 50px;
    border-radius: 100%;
    margin-top: 5px;
  }
  }
  .el-menu--horizontal {
  li:nth-child(3) {
    width: 15px;
  .el-submenu__title {
    height: 61px;
    background: red;
  .el-submenu__icon-arrow {
    top: 64%;
    right: 48px;
  }
  }
  }
  }
</style>
