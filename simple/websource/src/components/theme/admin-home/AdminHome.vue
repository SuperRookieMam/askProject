<template>
  <div>
    <el-row v-for="(val,i) in data" :key="i">
      <el-col :span="6" v-for="(item, index) in val" :key="index">
        <el-card :body-style="{ padding: '0px' }">
          <img src="../../../assets/girl.jpg" class="image">
          <div style="padding: 14px;">
            <h3><a @click="toHome(item)">{{ item.name + '控制台' }}</a></h3>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../plugins/TableBase'
  import { Mutation } from 'vuex-class'
  @Component
  export default class AdminHome extends Mixins(TableBase) {
    data = {}

    params = {}

    @Mutation('currentServer')
    currentServer

    toHome (server) {
      sessionStorage.setItem('currentServer', JSON.stringify(server))
      sessionStorage.setItem('servername', server.name)
      this.currentServer(server)
      this.$router.push({path: '/leftmain'})
    }

    getservers () {
      this.select(`${this.serverUrl.base.resourceServer}${this.serverUrl.resource.resourceServer}`, this.params, true).then(data => {
        let m = 3
        let key = 0
        let temp = {}
        data.forEach(ele => {
          if (m === 3) {
            temp[key] = [ele]
            m -= 1
          } else if (m > 1) {
            temp[key].push(ele)
            m -= 1
          } else if (m === 1) {
            temp[key].push(ele)
            m = 3
          }
        })
        this.data = temp
      })
    }

    created () {
      this.getservers()
    }
  }
</script>
<style>
  .parent {
    width: 100%;
    height: 100%;
  }
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>
