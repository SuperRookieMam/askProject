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
            <el-button type="primary" @click="submitForm('formData')">
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
              <el-form-item label="题目类型" prop="subjectName">
                <el-input v-model="formData.subjectName"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="题目描述" prop="description">
                <el-input type="textarea" :rows="3" v-model="formData.description"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="答案类型" prop="choose">
                <el-input v-model="formData.choose"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="答案设置" name="results" v-if="id!=='new'">
          <results
            current-html="rtable"
            jump-name="result"
            :rmsg="'Exam_results_'+formData.id"
            v-if="activeName === 'results'"/>
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import {Component, Prop, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import Results from '../Result/Results.vue'

  @Component({
    components: {
      Results
    }
  })
  export default class Exam extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    @Prop({default: () => ''})
    rmsg
    @Prop({default: () => ''})
    currentHtml

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.examUpdate)
    }

    rules = {}
  }
</script>
