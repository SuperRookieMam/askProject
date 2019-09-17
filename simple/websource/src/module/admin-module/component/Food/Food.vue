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
              <el-form-item label="菜品名" prop="foodName">
                <el-input v-model="formData.foodName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="描述" prop="description">
                <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 6}" v-model="formData.description"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="图片" prop="imgUrl">
                <el-upload
                  class="upload-demo"
                  :action="geturl(serverUrl.file.upload)"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemoveImgUrl"
                  :limit="1"
                  :file-list="fileList"
                  :http-request="uploadFileImgUrl"
                  list-type="picture-card">
                  <i class="el-icon-plus"/>
                  <div slot="tip" class="el-upload__tip"/>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="烹饪过程" prop="processUrls">
                <el-upload
                  class="upload-demo"
                  :action="geturl(serverUrl.file.upload)"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemoveProcessUrls"
                  :limit="1"
                  :file-list="fileList2"
                  :http-request="uploadFileProcessUrls"
                  list-type="picture-card">
                  <i class="el-icon-plus"/>
                  <div slot="tip" class="el-upload__tip"/>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="题目设置" name="exams">
          <!--<exams v-if="examsVisbale"/>-->
          <exam v-for="item in formData.exams"
                :exam="item"
                :key="item"
                @add-exam="andExam"
                :v-if="activeName === 'exams'"/>
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
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import {Component, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import Exam from '../Exams/Exam'
  @Component({
    components: {
      Exam
    }
  })
  export default class Food extends Mixins(TableBase) {
    currentHtml = 'food'
    examsVisbale = false
    getFromUrl () {
      return this.geturl(this.serverUrl.ask.foodUpdate)
    }

    rules = {}
    fileList= []
    fileList2= []

    formData = {exams:
        [{subjectName: '味型', results: [{}, {}, {}, {}], choose: '4选1', chooseNum: 4, rightNum: 1},
      {subjectName: '主料', results: [{}, {}, {}, {}, {}, {}, {}, {}], choose: '8选4', chooseNum: 8, rightNum: 4},
      {subjectName: '辅料', results: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}], choose: '10选6', chooseNum: 10, rightNum: 6},
      {subjectName: '调料', results: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}], choose: '15选10', chooseNum: 15, rightNum: 10},
      {subjectName: '成品特色', results: [{}, {}, {}, {}], choose: '4选1', chooseNum: 4, rightNum: 1},
      {subjectName: '渊源及文化', results: [{}, {}, {}, {}, {}, {}], choose: '6选3', chooseNum: 6, rightNum: 3}]
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
    uploadFileProcessUrls (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.geturl(this.serverUrl.file.preview)}${data.previewPath}`
        data.name = data.filename
        this.fileList2 = result
        this.formData.processUrls = `${data.previewPath}`
      })
    }

    // 菜品图片
    handleRemoveImgUrl (file, fileList) {
      this.remove(this.geturl(this.serverUrl.file.delete)).then(data => {
        this.fileList = []
        this.formData.imgUrl = ''
      })
    }
    // 烹调过程图片
    handleRemoveProcessUrls (file, fileList) {
      this.remove(this.geturl(this.serverUrl.file.delete)).then(data => {
        this.fileList2 = []
        this.formData.processUrls = ''
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

    sucessResult (data) {
      this.formData = data
      this.exams = data.exams
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
    }

    handleClick (tab, event) {
      if (tab.name === 'exams') {
        let params = this.getParames('food')
        this.setParames('exams', {
          type: 'rtable',
          tableName: 'Food',
          fileName: 'exams',
          rid: params.id,
          parent: this
        })
        this.examsVisbale = true
      }
    }

    eidtLastFormData (formData) {
      console.log(formData)
      // 数量
      if (formData.exams === undefined || formData.exams === null || formData.exams === '' || formData.exams.length !== 6) {
        this.message('该菜品的题目必须为6道', '友情提示')
        return false
      }
      // 分值
       let totalScore = 0
      for (var t in formData.exams) {
          if (formData.exams[t].results === undefined || formData.exams[t].results === '' || formData.exams[t].results === null) {
            this.message(formData.exams[t].subjectName + ' 试题答案为空', '友情提示')
            return false
          } else {
            let score = 0
            for (var j in formData.exams[t].results) {
              if (formData.exams[t].results[j].right === true) {
                score = Number.parseInt(score) + Number.parseInt(formData.exams[t].results[j].score)
              }
            }
            console.log(score)
            console.log(formData.exams[t].score)
            if (Number.parseInt(score) !== Number.parseInt(formData.exams[t].score)) {
              this.message(formData.exams[t].subjectName + '试题的总分与对应正确答案总分不符，请检查', '友情提示')
              return false
            }
            totalScore = totalScore + score
          }
      }
      // 判断总分是否为100
      if (totalScore !== 100) {
        this.message('该菜品试题总分应为100', '友情提示')
        return false
      }
      return true
    }

    andExam (exam) {
      // 检查 是否为空
      for (var o in this.formData.exams) {
        if (exam.subjectName === this.formData.exams[o].subjectName) {
          this.formData.exams[o] = exam
        }
      }
    }
  }
</script>
