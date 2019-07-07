<template>
  <el-container id="app">
    <el-header>
      <el-row class="title-wrapper">
        <el-col :span="12">
          <router-link to="/" class="title">后台管理</router-link>
        </el-col>
        <el-col :span="12">
          <el-menu mode="horizontal" background-color="#1D2023">
            <el-menu-item index="0"
                          style="padding: 0 15px;"
                          class="avatar">
              <img src="../../../assets/girl.jpg" alt="">
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">{{ displayUsername }}</template>
              <!-- <el-menu-item @click="doLogout" index="2-1" style="color: white">退出</el-menu-item>-->
            </el-submenu>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside class="left-template">
        <el-menu :collapse="true"
                 style="min-height: 120px"
                 text-color="#FFF"
                 background-color="#1D2023">
          <menu-item v-for="(menu,index) in menus"
                     :key="index"
                     :item="menu"/>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import { Action, Getter } from 'vuex-class'
  import MenuItem from '../MenuItem'

  @Component({
    components: {
      MenuItem
    }
  })
  export default class Leftmain extends Vue {
    @Getter('user')
    currentUser
    @Getter('menus')
    menus

    @Action('loadMenu')
    loadMenu

    get displayUsername () {
      return this.currentUser.username
    }
    created () {
    }
  }
</script>
<style lang="less">
  * {
    margin: 0;
    padding: 0;
  }

  html, body, #app {
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
