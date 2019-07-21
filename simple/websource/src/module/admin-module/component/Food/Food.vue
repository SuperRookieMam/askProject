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
              <el-form-item label="菜品名" prop="foodName">
                <el-input v-model="formData.foodName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="描述" prop="description">
                <el-input v-model="formData.description"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜品图片" prop="processUrls">
                <el-input v-model="formData.imgUrl"/>
                <el-upload
                  class="upload-demo"
                  :action="fileserver + fileMapping"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemoveImg"
                  :file-list="fileListImg"
                  :http-request="uploadFileImg"
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
                <el-input v-model="formData.processUrls"/>
                <el-upload
                  class="upload-demo"
                  :action="fileserver + fileMapping"
                  :before-upload="beforeAvatarUpload"
                  :on-remove="handleRemove"
                  :file-list="fileList"
                  :http-request="uploadFile"
                  list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="exams" prop="exams">
                <el-input v-model="formData.exams"/>
              </el-form-item>
            </el-col>
          </el-row>
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
        </el-tab-pane>
        <el-tab-pane label="题目" name="exm">
          <el-button type="primary"
                     size="mini"
                     @click="addExam()">
            新增题目
          </el-button>
          <el-dialog title="根节点信息录入" :visible.sync="dialogFormVisible">
            <el-form :model="newExam" ref="rootForm" :rules="rules">
              <el-form-item label="id" prop="id">
                <el-col :span="18">
                  <el-input v-model="newExam.id"/>
                </el-col>
              </el-form-item>
              <el-form-item label="题目" prop="subjectName">
                <el-col :span="18">
                  <el-input v-model="newExam.subjectName"/>
                </el-col>
              </el-form-item>
              <el-form-item label="题型" prop="choose">
                <el-select v-model="newExam.choose" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
              <el-form-item label="描述" prop="description">
                <el-col :span="18">
                  <el-input v-model="newExam.description"/>
                </el-col>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="cancelAddRoot">取 消</el-button>
              <el-button type="primary" @click="addExamTable('')">确 定</el-button>
            </div>
          </el-dialog>

          <el-table
            :data="examTable"
            style="width: 100%">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="id">
                    <span>{{ props.row.id }}</span>
                  </el-form-item>
                  <el-form-item label="题目">
                    <span>{{ props.row.subjectName }}</span>
                  </el-form-item>
                  <el-form-item label="题型">
                    <span>{{ props.row.choose }}</span>
                  </el-form-item>
                  <el-form-item label="描述">
                    <span>{{ props.row.description }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="id"
              prop="id"/>
            <el-table-column
              label="题目"
              prop="subjectName"/>
            <el-table-column
              label="题型"
              prop="choose"/>
            <el-table-column
              label="描述"
              prop="description">
              <template slot-scope="scope">
                <el-popover
                  ref="popover4"
                  placement="right"
                  width="800"
                  trigger="click">
                  <el-dialog title="新增答案" :visible.sync="dialogFormVisibleResult">
                    <el-form :model="newResult" ref="rootForm" :rules="rules">
                      <el-form-item label="id" prop="id">
                        <el-col :span="18">
                          <el-input v-model="newResult.id"/>
                        </el-col>
                      </el-form-item>
                      <el-form-item label="描述" prop="description">
                        <el-col :span="18">
                          <el-input v-model="newResult.description"/>
                        </el-col>
                      </el-form-item>
                      <el-form-item label="正确？" prop="right">
                        <el-select v-model="newResult.right" placeholder="请选择">
                          <el-option
                            v-for="item in options2"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"/>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="分值" prop="score">
                        <el-col :span="18">
                          <el-input v-model="newResult.score"/>
                        </el-col>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="cancelAddRoot">取 消</el-button>
                      <el-button type="primary" @click="addResultTable()">确 定</el-button>
                    </div>
                  </el-dialog>
                  <el-button type="primary"
                             size="mini"
                             @click="addResultView()">
                    新增答案
                  </el-button>
                  <el-button type="primary"
                             size="mini"
                             @click="addResultToExam(scope.row)">
                    确定将答案放入试题
                  </el-button>
                  <el-table :data="resultTable">
                    <el-table-column
                      label="题型"
                      prop="scope.row.choose"/>
                    <el-table-column
                      label="题目"
                      prop="scope.row.resultTable"/>
                    <el-table-column width="150" property="id" label="id"></el-table-column>
                    <el-table-column width="100" property="description" label="描述"></el-table-column>
                    <el-table-column width="300" property="score" label="得分"></el-table-column>
                    <el-table-column width="300" property="right" label="是否正确"></el-table-column>
                  </el-table>
                </el-popover>
                <el-button v-popover:popover4>添加答案</el-button>
              <el-button type="text" size="mini" @click="deleteExam(scope.row)">删除试题</el-button>
                <el-button type="text" size="mini" @click="editExam(scope.row)">修改试题</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-row>
            <el-col :span="12">
              <el-form-item>
                <el-button type="primary" @click="submitExam()">
                  确认
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
  import {Component, Prop, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class Food extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    activeName = 'base'
    options = [{
      value: '单选题',
      label: '单选题'
    }, {
      value: '多选题',
      label: '多选题'
    }]
    options2=[{
      value: 'true',
      label: '正确'
    }, {
      value: 'false',
      label: '错误'
    }]

    examTable = []
    resultTable=[]
    dialogFormVisible = false
    dialogFormVisibleResult=false
    //试题字符串
    newExam = {
      id: '',
      subjectName: '',
      description: '',
      choose: '',
      results: []
    }
    // 答案字符串
    newResult={
      id:'',
      description:'',
      score:'',
      right:''
    }
    controllerMapping = 'data/food'

    fileList = []

    fileListImg=[]
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

    // 删除烹调图片
    handleRemove (file, fileList) {
      this.remove(`data/${this.fileMapping}/${this.fileList[0].id}`).then(data => {
        this.fileList = []
        this.formData.processUrls = ''
      })
    }

    // 删除菜品图片
    handleRemoveImg (file, fileList) {
      this.remove(`data/${this.fileMapping}/${this.fileList[0].id}`).then(data => {
        this.fileListImg = []
        this.formData.imgUrl = ''
      })
    }

    uploadFile (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.fileserver}${this.fileMapping}${data.previewPath}`
        data.name = data.filename
        this.fileList = result
        this.formData.processUrls = `${data.previewPath}/${data.id}`
      })
    }

    uploadFileImg (params) {
      this.upfile(params).then(result => {
        let data = result[0]
        data.url = `${this.fileserver}${this.fileMapping}${data.previewPath}`
        data.name = data.filename
        this.fileListImg = result
        this.formData.imgUrl = `${data.previewPath}/${data.id}`
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

    // 为菜单添加试题
    addExamTable () {
      this.examTable.push(this.newExam)
      console.log(this.newExam)
      this.dialogFormVisible = false
      this.newExam = {
        id: '',
        subjectName: '',
        description: '',
        choose: '',
        results: []
      }
    }
    //删除选中试题
    deleteExam(exam){
      for (var m = 0; m < this.examTable.length; m++) {
        if (this.examTable[m] === exam) {
          this.examTable.splice(m, 1);
          return;
        }
      }
    }
    addResultTable(){
      this.resultTable.push(this.newResult)
      this.newResult={
        id:'',
        description:'',
        score:'',
        right:''
      }
      this.dialogFormVisibleResult=false
      console.log(this.resultTable)
    }
    // 取消为菜单添加试题
    cancelAddRoot () {
      alert('取消添加试题')
      this.dialogFormVisible = false
    }

    // 确定将试题放入菜品
    submitExam () {
      this.formData.exams = this.examTable
      console.log('+++++++++++')
      console.log(this.formData)
    }

  // 为试题添加答案
    addResultToExam (exam) {
      console.log(exam)
      this.newExam=exam
      this.newExam.results=this.resultTable
      for (var m = 0; m < this.examTable.length; m++) {
        if (this.examTable[m].subjectName=== exam.subjectName) {
          this.examTable.splice(m, 1);
          this.examTable.push(this.newExam)
          console.log(this.newExam)
          return;
        }
      }

    }


    //修改试题
    editExam(exam){
    this.dialogFormVisible=true
      this.newExamt=exam

    }

    // 移除菜品图片
    handleRemoveImgUrl (file) {
      console.log('删除菜品图片')
      var imgUrls = this.formData.imgUrl.split(',')
      var i = imgUrls.length
      var t = -1
      while (i--) {
        if (imgUrls[i] === file.response[0].path) {
          t = i
          break
        }
      }

      imgUrls.splice(t, 1)
      var txt = ''
      if (imgUrls.length !== 0) {
        for (var x in imgUrls) {
          console.log(x)
          if (x === 0) {
            txt = txt + imgUrls[x]
          } else {
            txt = txt + ',' + imgUrls[x]
          }
        }
      }

      this.formData.imgUrl = txt
      console.log(this.formData.imgUrl)
    }

    // 添加烹饪
    handleAvatarSuccessProcess (response, file, fileList) {
      console.log('添加烹饪图片')
      // 获取图片URL
      var imgUrls = ''
      imgUrls = this.formData.processUrls
      if (imgUrls === '' || undefined === imgUrls) {
        imgUrls = imgUrls = file.response[0].path
      } else {
        imgUrls = imgUrls = imgUrls + ',' + file.response[0].path
      }
      this.formData.processUrls = imgUrls
      console.log(this.formData.processUrls)
    }

    // 移除烹饪图片
    handleRemoveProcess (file) {
      console.log('删除烹饪图片')
      var imgUrls = this.formData.processUrls.split(',')
      var i = imgUrls.length
      var t = -1
      while (i--) {
        if (imgUrls[i] === file.response[0].path) {
          t = i
          break
        }
      }

      imgUrls.splice(t, 1)
      var txt = ''
      if (imgUrls.length !== 0) {
        for (var x in imgUrls) {
          if (x === 0) {
            txt = txt + imgUrls[x]
          } else {
            txt = txt + ',' + imgUrls[x]
          }
        }
      }

      this.formData.processUrls = txt
      console.log(this.formData.processUrls)
    }

    // 开启隐藏新增菜品页面
    addExam () {
      this.dialogFormVisible = true
    }
    addResultView(){
      this.dialogFormVisibleResult=true
    }
    created () {
      this.getFormData(this.controllerMapping, this.id).then(data =>{
        console.log(data)
        if (data.exams !== null) {
          this.examTable = data.exams
        }
      })


    }
  }
</script>
