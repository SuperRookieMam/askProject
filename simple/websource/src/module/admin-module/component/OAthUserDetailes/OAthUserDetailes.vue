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
              <el-form-item label="账号" prop="username">
                <el-input v-model="formData.username"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="密码" prop="password">
                <el-input v-model="formData.password"/>
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
              <el-form-item label="微信账号" prop="nickname">
                <el-input v-model="formData.weChatId"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="凭证" prop="credentials">
                <el-input v-model="formData.credentials"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="头像" prop="headImage">
                <el-upload
                  class="upload-demo"
                  :action="geturl(serverUrl.file.upload)"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemove"
                  :limit="1"
                  :file-list="fileList"
                  :http-request="uploadFile"
                  list-type="picture-card">
                  <i class="el-icon-plus"/>
                  <div slot="tip" class="el-upload__tip"/>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="过期" prop="expired">
                <el-select v-model="formData.expired" placeholder="请选择">
                  <el-option
                    label="否"
                    :value="false"/>
                  <el-option
                    label="是"
                    :value="true"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="锁定" prop="lock">
                <el-select v-model="formData.lock" placeholder="请选择">
                  <el-option
                    label="否"
                    :value="false"/>
                  <el-option
                    label="是"
                    :value="true"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="启用" prop="enabled">
                <el-select v-model="formData.enabled" placeholder="请选择">
                  <el-option
                    label="是"
                    :value="true"/>
                  <el-option
                    label="否"
                    :value="false"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
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
    </el-form>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class OAthUserDetailes extends Mixins(TableBase) {
    currentHtml = 'oAthUserDetailes'

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.oauthUserDetailsUpdate)
    }
    fileList = []
    rules = {
      name: [
        {required: true, message: '请输入活动名称', trigger: 'blur'},
        {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
      ]
    }

    handleRemove (file, fileList) {
      this.remove(this.geturl(this.serverUrl.file.delete)).then(data => {
        this.fileList = []
        this.formData.headImage = ''
      })
    }

    uploadFile (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.geturl(this.serverUrl.file.preview)}${data.previewPath}`
        data.name = data.filename
        this.fileList = result
        this.formData.headImage = `${data.previewPath}`
      })
    }

    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg' ||
        file.type === 'image/png' ||
        file.type === 'image/jpg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.message('上传头像图片只能是 JPG png jpeg 格式!', '友情提示')
      }
      if (!isLt2M) {
        this.message('上传头像图片大小不能超过 2MB!', '友情提示')
      }
      return isJPG && isLt2M
    }

    eidtLastFormData (formName) {
      delete this.formData.accountNonExpired
      delete this.formData.accountNonLocked
      delete this.formData.credentialsNonExpired
    }
    sucessResult (data) {
      this.formData = data
      this.fileList = [{
        url: `${this.geturl(this.serverUrl.file.preview)}${this.formData.headImage}`,
        name: '当前头像'
      }]
    }
  }
</script>
