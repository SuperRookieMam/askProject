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
                <el-select v-model="temp" placeholder="请选择" @change="changeValue">
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
            <el-button type="primary" @click="submitForm1('formData')">
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
  @Component({
    components: {
    }
  })
  export default class Result extends Mixins(TableBase) {
    currentHtml = 'result'

    temp = '正确'

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
      if (val === '正确') {
        this.formData.right = true
      } else {
        this.formData.right = false
      }
    }
    rules = {}

    submitForm1 (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let params = this.getParames(this.currentHtml)
          if (!params.parent[params.fileName]) {
            params.parent[params.fileName] = []
          }
          for (let i = 0; i < params.parent[params.fileName].length; i++) {
            if (params.parent[params.fileName][i].id && params.parent[params.fileName][i].id === this.formData.id) {
              params.rparent.dialogVisible1 = false
              return
            }
          }
          params.parent[params.fileName].push(this.formData)
          params.rparent.dialogVisible1 = false
        } else {
            return false
        }
      })
  }

   created () {
     let params = this.getParames(this.currentHtml)
      if (params.id !== 'new') {
          this.formData = params.data
      }
      if (this.formData.right) {
        this.temp = '正确'
      } else {
        this.temp = '错误'
      }
   }
  }
</script>
