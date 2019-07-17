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
              <el-form-item label="图片url" prop="imgUrl">
                <el-input v-model="formData.imgUrl"/>
                <el-upload
                  class="upload-demo"
                  action="http://localhost:8002/fileInfo"
                  :on-remove="handleRemoveImgUrl"
                  list-type="picture"
                 :on-success="handleAvatarSuccessImgUrl">
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
                                  action="http://localhost:8002/fileInfo"
                                  :on-remove="handleRemoveProcess"
                                  list-type="picture"
                                 :on-success="handleAvatarSuccessProcess">
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
                                                           :value="item.value">
                                                         </el-option>
                                                       </el-select>
                                                   </el-form-item>
                                                   <el-form-item label="描述" prop="description">
                                                     <el-col :span="18">
                                                       <el-input v-model="newExam.description"/>
                                                     </el-col>
                                                   </el-form-item>
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
                      <span>{{ props.row.choose}}</span>
                    </el-form-item>
                    <el-form-item label="描述">
                      <span>{{ props.row.description }}</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                label="id"
                prop="id">
              </el-table-column>
              <el-table-column
                label="题目"
                prop="subjectName">
              </el-table-column>
               <el-table-column
                  label="题型"
                  prop="choose">
                </el-table-column>
              <el-table-column
                label="描述"
                prop="description">
                           <el-button type="text" size="mini" @click="addResults(scope.row)">添加答案</el-button>
                             <el-button type="text" size="mini" @click="deleteRow(scope.row)">删除试题</el-button>
              </el-table-column>
            </el-table>
        </el-tab-pane>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <el-button type="primary" @click="submitExam()">
                保存
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
  export default class Food extends Mixins(TableBase) {
    @Prop({ default: () => 'new' })
    id
   activeName = 'base'
    options= [{
             value: '单选题',
             label: '单选题'
           }, {
             value: '多选题',
             label: '多选题'
           }]

       examTable=[]
   dialogFormVisible=false;
   controllerMapping = 'data/food'
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
    //添加菜品图片
     handleAvatarSuccessImgUrl(response, file, fileList) {
     console.log("添加菜品图片")
            //获取图片URL
            var imgUrls=""
            imgUrls= this.formData.imgUrl
           if(""===imgUrls||undefined===imgUrls){
           imgUrls= imgUrls=file.response[0].path
            }else{
             imgUrls= imgUrls= imgUrls+","+file.response[0].path
           }
           this.formData.imgUrl=imgUrls
           console.log(this.formData.imgUrl)
      }
      //为菜单添加试题
      addExamTable(){
      console.log(this.newExam)
        this.examTable.push(this.newExam)
        console.log(this.newExam)
        this.dialogFormVisible=false;
      }
      //取消为菜单添加试题
      cancelAddRoot(){
            alert("取消添加试题")
             this.dialogFormVisible=false;
         }
      //确定将试题放入菜品
      submitExam(){
          this.formData.exams  =this.examTable
          console.log("+++++++++++")
          console.log(this.formData)

      }
//为试题添加答案
      addResults(row){
         console.log(row)
      }
      //删除试题
      deleteRow(row){
      console.log(row)
      }
      //移除菜品图片
      handleRemoveImgUrl(file){
      console.log("删除菜品图片")
        var imgUrls=this.formData.imgUrl.split(",")
         var i = imgUrls.length;
         var t=-1;
         while (i--) {
          if (imgUrls[i] === file.response[0].path) {
          t=i;
           break;
          }
         }

         imgUrls.splice(t,1);
        var txt="";
        if(imgUrls.length!==0){
           for(var i in imgUrls ){
             console.log(i)
                  if(i==0){
                    txt=txt+imgUrls[i]
                  }else{
                   txt=txt+","+imgUrls[i]
                  }
            }
          }

        this.formData.imgUrl=txt
        console.log(this.formData.imgUrl)
      }


       //添加烹饪
           handleAvatarSuccessProcess(response, file, fileList) {
           console.log("添加烹饪图片")
                  //获取图片URL
                  var imgUrls=""
                  imgUrls= this.formData.processUrls
                 if(""===imgUrls||undefined===imgUrls){
                 imgUrls= imgUrls=file.response[0].path
                  }else{
                   imgUrls= imgUrls= imgUrls+","+file.response[0].path
                 }
                 this.formData.processUrls=imgUrls
                 console.log(this.formData.processUrls)
            }
            //移除烹饪图片
            handleRemoveProcess(file){
             console.log("删除烹饪图片")
              var imgUrls=this.formData.processUrls.split(",")
               var i = imgUrls.length;
               var t=-1;
               while (i--) {
                if (imgUrls[i] === file.response[0].path) {
                t=i;
                 break;
                }
               }

               imgUrls.splice(t,1);
              var txt="";
              if(imgUrls.length!==0){
                 for(var i in imgUrls ){
                        if(i==0){
                          txt=txt+imgUrls[i]
                        }else{
                         txt=txt+","+imgUrls[i]
                        }
                  }
                }

              this.formData.processUrls=txt
              console.log(this.formData.processUrls)
            }


            //开启隐藏新增菜品页面
            addExam(){
              this.dialogFormVisible=true;
            }
            newExam={
            id:"",
            subjectName:"",
            description:"",
            choose:"",
            results:[]
            }
    created () {
     this.getFormData(this.controllerMapping, this.id)
     console.log(this.formData)
     if(null!=this.formData.exams){
          this.examTable=this.formData.exams
     }
    }
  }
</script>
