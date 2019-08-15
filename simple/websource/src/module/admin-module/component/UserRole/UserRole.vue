<template>
  <div>
    <el-form :model="formData"
             :rules="rules"
             ref="formData"
             label-width="100px"
             v-loading="loading"
             size="mini">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基础信息" name="base">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户账号" prop="userName">
                <el-autocomplete
                  v-model="formData.userName"
                  :fetch-suggestions="remoteUser"
                  placeholder="请输入内容"
                  @select="handleSelect"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="角色名称" prop="roleName">
                <el-autocomplete
                  v-model="formData.roleName"
                  :fetch-suggestions="roleUser"
                  placeholder="请输入内容"
                  @select="handleSelect"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="formData.nickname" :disabled="true"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="角色编号" prop="roleId">
                <el-input v-model="formData.roleId" :disabled="true"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <el-button type="primary" @click="submitForm('formData')">
                保存
              </el-button>
              <el-button @click="resetForm('formData')">
                取消
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class UserRole extends Mixins(TableBase) {
    currentHtml = 'userRole'

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.userRoleUpdate)
    }

    timeout = null

    rules = {}

    remoteUser (query, cb) {
      if (query && query !== '') {
        clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          this.loading = false
          let params = {}
          let flatParams = []
          flatParams.push({type: 'or',
            basicsParams: [{key: 'username', type: 'like', value: query},
                           {key: 'nickname', type: 'like', value: query}]})
          params.flatParams = flatParams
          this.select(this.geturl(this.serverUrl.shopping.oauthUserDetailsList), params, true).then(data => {
             let reults = data.map(ele => {
              ele.value = ele.username
              return ele
            })
            cb(reults)
          })
        }, 200)
      }
    }
    roleUser (query, cb) {
      if (query && query !== '') {
        clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          this.loading = false
          let params = {}
          let basicsParams = []
          basicsParams.push({key: 'roleName', type: 'like', value: query})
          params.basicsParams = basicsParams
          this.select(this.geturl(this.serverUrl.ask.roleList), params, true).then(data => {
            let reults = data.map(ele => {
              ele.value = ele.roleName
              return ele
            })
            cb(reults)
          })
        }, 200)
      }
    }
    createStateFilter (queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    }
    handleSelect (item) {
      if (item.username) {
        this.formData.nickname = item.nickname
      } else {
        this.formData.roleId = item.id
      }
    }
    replaceJump () {
      this.reload()
    }
  }
</script>
<style>
  .my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;
    .name {
      text-overflow: ellipsis;
      overflow: hidden;
      }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
      }
    .highlighted .addr {
      color: #ddd;
      }
    }
  }
</style>
