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
              <el-form-item label="答案" prop="description">
                <el-input v-model="formData.description"/>
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
              <el-form-item label="是否正确" prop="right">
                <el-input v-model="formData.right"/>
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
  export default class Result extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    @Prop({default: () => ''})
    rmsg
    @Prop({default: () => ''})
    currentHtml

    getFromUrl () {
      return this.geturl(this.serverUrl.ask.resultUpdate)
    }

    rules = {}
  }
</script>
