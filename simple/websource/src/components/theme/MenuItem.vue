<template>
  <el-submenu
    v-if="item.submenus && item.submenus.length>0"
    popper-class="my-submenu"
    :index="item.id+''">
    <template slot="title"> {{ item.cname }}</template>
    <menu-item v-for="(submenu,index) in item.submenus"
               :item="submenu"
               :key="index"/>
  </el-submenu>
  <el-menu-item v-else
                @click="toTarget(item)"
                :index="item.id+''">
    <template>{{ item.cname }}</template>
  </el-menu-item>
</template>

<script>
  import Vue from 'vue'
  import { Component, Prop } from 'vue-property-decorator'

  @Component({
    name: 'menu-item'
  })
  export default class MenuItem extends Vue {
    @Prop({default: () => {}})
    item

    toTarget ({isMenu, routeName}) {
      if (isMenu === 1) {
        this.$router.push({name: routeName})
      }
    }
  }
</script>

<style scoped>

</style>
