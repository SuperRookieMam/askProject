<template>
  <el-submenu v-if="item.submenus && item.submenus.length>0"
              popper-class="my-submenu"
              :index="item.id+''">
    <template slot="title">
      <i class="el-icon-menu"/>
      <span slot="title">{{ item.cname }}</span>
    </template>
    <menu-item v-for="(submenu,index) in item.submenus"
               :item="submenu"
               :key="index"/>
  </el-submenu>
  <el-menu-item v-else
                @click="toTarget(item)"
                :index="item.id+''">
    <i class="el-icon-menu"/>
    <span slot="title">{{ item.cname }}</span>
  </el-menu-item>
</template>

<script>
  import Vue from 'vue'
  import { Component, Prop } from 'vue-property-decorator'

  @Component({
    name: 'menu-item',
    inject: ['setParames']
  })
  export default class MenuItem extends Vue {
    @Prop({default: () => {}})
    item

    toTarget (item) {
      if (item.isMenu === 1) {
        this.setParames(item.tableRoute, {type: item.type, formRoute: item.formRoute})
        this.$router.push({path: `/${item.tableRoute}`})
      }
    }
  }
</script>
