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
          <!--<el-row>-->
          <!--<el-col :span="12">-->
          <!--<el-form-item label="所属菜品" prop="score">-->
          <!--<many-to-one-cascader-->
          <!--:data-arr="foods"-->
          <!--@select-change="selectChange"-->
          <!--:props="props"-->
          <!--:rule-arr="ruleArr"-->
          <!--v-if="temp"/>-->
          <!--</el-form-item>-->
          <!--</el-col>-->
          <!--</el-row>-->
          <el-row>
            <el-col :span="12">
              <el-form-item label="题目类型" prop="subjectName">
                <el-select
                  v-model="formData.subjectName"
                  @change="changeSelect"
                  placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="答案类型" prop="choose">
                <el-input v-model="formData.choose" :disabled="true"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="分值" prop="score">
                <el-input type="number" v-model="formData.score"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="题目描述" prop="description">
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
  export default class Exam extends Mixins(TableBase) {
    currentHtml = 'exam'
    options = [{label: '味型', value: '味型', type: '四选一'},
                {label: '主料', value: '主料', type: '八选四'},
                {label: '辅料', value: '辅料', type: '十选六'},
                {label: '调料', value: '调料', type: '十五选十'},
                {label: '成品特色', value: '成品特色', type: '四选一'},
                {label: '渊源及文化', value: '渊源及文化', type: '六选三'}]

    changeSelect () {
      this.options.forEach(ele => {
        if (this.formData.subjectName === ele.value) {
          this.formData.choose = ele.type
          if (ele.type === '四选一') {
            this.formData.chooseNum = 4
            this.formData.rightNum = 1
          } else if (ele.type === '八选四') {
            this.formData.chooseNum = 8
            this.formData.rightNum = 4
          } else if (ele.type === '十选六') {
            this.formData.chooseNum = 10
            this.formData.rightNum = 6
          } else if (ele.type === '六选三') {
            this.formData.chooseNum = 6
            this.formData.rightNum = 3
          } else if (ele.type === '十五选十') {
            this.formData.chooseNum = 15
            this.formData.rightNum = 10
          } else {
            this.message('无此选择类型请选择，请检查', '友情提示')
          }
        }
      })
    }

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.examUpdate)
    }

    replaceJump (data) {
      let params = this.getParames('exam')
      this.formData = data
      params.parent.dialogVisible = false
    }

    rules = {}
  }
</script>
