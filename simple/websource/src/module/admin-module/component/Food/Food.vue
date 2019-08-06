<template>
  <div>
    <el-form :model="formData"
             :rules="rules"
             ref="formData"
             label-width="100px"
             v-loading="loading"
             size="mini">
      <el-row>
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" @click="submitFormValidation('formData')">
              保存
            </el-button>
            <el-button @click="resetForm('formData')">
              取消
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基础信息" name="base">
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜品名" prop="foodName">
                <el-input v-model="formData.foodName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="描述" prop="description">
                <el-input type="textarea" :rows="2" v-model="formData.description"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="图片url" prop="imgUrl">
                <el-upload
                  class="upload-demo"
                  :action="geturl(serverUrl.file.upload)"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemoveImgUrl"
                  :limit="1"
                  :file-list="fileList"
                  :http-request="uploadFileImgUrl"
                  list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="烹饪过程url" prop="processUrls">
                <el-upload
                  class="upload-demo"
                  :action="geturl(serverUrl.file.upload)"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemoveProcessUrls"
                  :limit="1"
                  :file-list="fileList2"
                  :http-request="uploadFileProcessUrls"
                  list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="题目设置" name="exams" v-if="id!=='new'">
          <exams
            current-html="rtable"
            jump-name="exam"
            :rmsg="'Food_exams_'+formData.id"
            v-if="activeName === 'exams'"/>
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import {Component, Prop, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import Exams from '../Exams/Exams'

  @Component({
    components: {
      Exams
    }
  })
  export default class Food extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    @Prop({default: () => ''})
    rmsg
    @Prop({default: () => ''})
    currentHtml

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.foodUpdate)
    }
    // 保存 判断总分必须为100 试题数量必须为6
    submitFormValidation (formName) {
      let arr = this.rmsg.split('_')
      this.formData.tableName = arr[0]
      this.formData[arr[1]] = arr[2]
      var totalScore = 0
      console.log('++++')
      console.log(this.formData)
      if (this.formData.exams.length !== 6) {
        alert('该菜品试题数量设置应为6道,您设置了' + this.formData.exams.length + '道')
        return
      }
      for (var i in this.formData.exams) {
        for (var j in this.formData.exams[i].results) {
          if (this.formData.exams[i].results[j].right === true) {
            totalScore = totalScore + this.formData.exams[i].results[j].score
          }
        }
      }
      if (totalScore > 100) {
        alert('该菜品试题总分设置大于100分')
        return
      }
      if (totalScore < 100) {
        alert('该菜品试题总分设置小于于100分')
        return
      }
      this.submitForm(formName)
    }
    rules = {}
    fileList= []
    fileList2= []
    // 菜品图片
    handleRemoveImgUrl (file, fileList) {
      this.remove(this.geturl(this.serverUrl.file.delete)).then(data => {
        this.fileList = []
        this.formData.imgUrl = ''
      })
    }
    uploadFileImgUrl (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.geturl(this.serverUrl.file.preview)}${data.previewPath}`
        data.name = data.filename
        this.fileList = result
        this.formData.imgUrl = `${data.previewPath}`
      })
    }
    // 烹调过程图片
    handleRemoveProcessUrls (file, fileList) {
      this.remove(this.geturl(this.serverUrl.file.delete)).then(data => {
        this.fileList2 = []
        this.formData.processUrls = ''
      })
    }
    uploadFileProcessUrls (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.geturl(this.serverUrl.file.preview)}${data.previewPath}`
        data.name = data.filename
        this.fileList2 = result
        this.formData.processUrls = `${data.previewPath}`
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
  getFormData () {
    if (this.id === 'new') {
      return
    }
    return this.select(`${this.getFromUrl()}/${this.id}`).then(data => {
      alert(1)
      console.log(data)
      this.formData = data
      if (this.formData.imgUrl) {
        this.fileList = [{
          url: `${this.geturl(this.serverUrl.file.preview)}${this.formData.imgUrl}`,
          name: this.formData.imgUrl
        }]
      }
      if (this.formData.processUrls) {
        this.fileList2 = [{
          url: `${this.geturl(this.serverUrl.file.preview)}${this.formData.processUrls}`,
          name: this.formData.processUrls
        }]
      }
      return data
    })
  }
  }
</script>
