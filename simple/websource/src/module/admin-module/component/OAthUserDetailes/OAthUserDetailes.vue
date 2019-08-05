<template>
  <div>
    <el-form :model="formData"
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
              <el-form-item label="username" prop="username">
                <el-input v-model="formData.username"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="formData.nickname"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="微信号" prop="weChatId">
                <el-input v-model="formData.weChatId"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="password" prop="password">
                <el-input v-model="formData.password"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="headImage" prop="headImage">
                <el-input v-model="formData.headImage"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="authorities" prop="authorities">
                <el-input v-model="formData.authorities"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="expired" prop="expired">
                <el-input v-model="formData.expired"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="lock" prop="lock">
                <el-input v-model="formData.lock"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="credentials" prop="credentials">
                <el-input v-model="formData.credentials"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="enabled" prop="enabled">
                <el-input v-model="formData.enabled"/>
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
  export default class OAthUserDetailes extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    @Prop({default: () => ''})
    rmsg
    @Prop({default: () => ''})
    currentHtml
    @Prop({ default: () => 'new' })
    id
    activeName = 'base'
   controllerMapping = 'data/oAthUserDetailes'
    rules = {
         name: [
            {required: true, message: '请输入活动名称', trigger: 'blur'},
            {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
          ]
        }
   submitForm (formName, id, url) {
    this.$refs[formName].validate((valid) => {
      if (valid) {
        if (id === 'new') {
          console.log('新增' + this.formData)
          this.insert({url: url, params: [this.formData]}).then(ele => {
              if (ele.code === 0) {
                this.formData = ele.data[0]
                this.$router.go(-1)
              } else {
                this.message(ele.msg, '友情提示')
              }
            })
        } else {
          console.log('修改' + JSON.stringify(this.formData))
          delete this.formData.accountN

          this.update({url: url, params: [this.formData]})
              .then(ele => {
              if (ele.code === 0) {
                this.formData = ele.data[0]
                this.$router.go(-1)
              } else {
               this.message(ele.msg, '友情提示')
              }
            })
        }
      } else {
        return false
      }
    })
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
