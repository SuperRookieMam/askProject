<template>
  <div>
    <el-form :model="formData"
             :rules="rules"
             ref="formData"
             v-loading="loading"
             label-width="100px"
             size="mini">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基础信息" name="base">
          <el-row>
            <el-col :span="12">
              <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="formData.roleName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="父ID" prop="pid">
                <el-input type="number" v-model="formData.pid"/>
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
  export default class RoleInfo extends Mixins(TableBase) {
    currentHtml = 'roleInfo'

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.roleInfoUpdate)
    }
    replaceJump () {
      this.reload()
    }
    rules = {}
  }
</script>
