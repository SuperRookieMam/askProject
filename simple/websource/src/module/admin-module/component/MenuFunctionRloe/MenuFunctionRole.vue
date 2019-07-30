<template>
  <div>
    <el-form
      :model="formData"
      :rules="rules"
      ref="formData"
      label-width="100px"
      size="mini">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基础信息" name="base">
          <el-row>
            <el-col :span="12">
              <el-form-item label="createTime" prop="createTime">
                <el-input v-model="formData.createTime"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="modifyTime" prop="modifyTime">
                <el-input v-model="formData.modifyTime"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="createUser" prop="createUser">
                <el-input v-model="formData.createUser"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="modifyUser" prop="modifyUser">
                <el-input v-model="formData.modifyUser"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="id" prop="id">
                <el-input v-model="formData.id"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="menuFunctionId" prop="menuFunctionId">
                <el-input v-model="formData.menuFunctionId"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="roleId" prop="roleId">
                <el-input v-model="formData.roleId"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="resourceId" prop="resourceId">
                <el-input v-model="formData.resourceId"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <el-button type="primary" @click="submitForm('formData',id,controllerMapping)">
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
  import { Component, Prop, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class MenuFunctionRole extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    activeName = 'base'
    controllerMapping = 'data1/menuFunctionRole'
    rules = {
      name: [
        {required: true, message: '请输入活动名称', trigger: 'blur'},
        {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
      ]
    }

    handleClick (tab, event) {
      /* if (tab.name === 'formtest') {
           this.$router.push({name: 'tt', params: {rowData: {id: 'new'}}})
         } */
    }

    created () {
      this.getFormData(this.controllerMapping, this.id)
    }
  }
</script>
