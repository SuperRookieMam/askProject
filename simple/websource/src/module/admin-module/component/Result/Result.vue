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
              <el-form-item label="是否正确" prop="right">
                <el-select v-model="formData.right" placeholder="请选择" @change="changeValue">
                  <el-option
                    v-for="item in optionsRight"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="所属题目">
                <many-to-one-cascader
                  :data-arr="exams"
                  @select-change="selectChange"
                  :props="props"
                  :rule-arr="ruleArr"
                  v-if="temp"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="分值" prop="score">
                <el-input v-model="formData.score"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="答案" prop="description">
                <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 6}" v-model="formData.description"/>
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
  import {Component, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import ManyToOneCascader from '../../../../components/cascader/ManyToOneCascader'
  @Component({
    components: {
      ManyToOneCascader
    }
  })
  export default class Result extends Mixins(TableBase) {
    currentHtml = 'result'

    optionsRight=[{
      value: 'true',
      label: '正确'
    }, {
      value: 'false',
      label: '错误'
    }]
    getFromUrl () {
      return this.geturl(this.serverUrl.ask.resultUpdate)
    }
    changeValue (val) {
    if (val === 'true') {
      return '正确'
    } else {
      return '错误'
    }
    }
    exams = []
    temp = false
    ruleArr = [{label: 'subjectName', value: 'id', parent: 'id', chiled: 'food'},
                {label: 'foodName', value: 'id', parent: 'id', chiled: 'root'}]
    sucessResult (data) {
      this.formData = data
      this.select(this.geturl(this.serverUrl.ask.examList), {}, true).then(data1 => {
        this.exams = data1
        this.temp = true
      })
    }
    selectChange (val) {
      for (let i = 0; i < this.exams.length; i++) {
        if (this.exams[i].id === val[val.length - 1]) {
          this.formData.exam = this.exams[i]
          break
        }
      }
    }
    props = {
      label: 'subjectName',
      value: 'id',
      children: 'children'
    }
    rules = {}
  }
</script>
