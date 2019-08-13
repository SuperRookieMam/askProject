<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <!--<el-col :span="6">-->
        <!--<el-form-item label="题目类型">-->
        <!--<el-input v-model="serchObj['subjectName']" placeholder="请输入"/>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="6">-->
        <!--<el-form-item label="所属菜品">-->
        <!--<el-input v-model="serchObj['food.foodName']" placeholder="请输入"/>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="3" style="float: right">
          <!--<el-button type="primary"-->
          <!--size="mini"-->
          <!--@click="filterByserchObj">-->
          <!--筛选-->
          <!--</el-button>-->
          <el-button type="primary"
                     size="mini"
                     @click="add">
            新增
          </el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-table :data="tableData"
              v-loading="loading"
              style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-table
            label-position="left"
            inline
            :data="scope.row.results"
            style="background-color: #dddddd"
            class="demo-table-expand">
            <el-table-column label="分值" prop="score"/>
            <el-table-column label="正确" prop="right" :formatter="statusFormatter"/>
            <el-table-column label="答案" prop="description"/>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="id" prop="id"/>
      <el-table-column label="题目类型" prop="subjectName"/>
      <el-table-column label="答案类型" prop="choose"/>
      <el-table-column label="分值" prop="score"/>
      <el-table-column label="题目描述" prop="description">
        <template slot-scope="scope">
          {{ scope.row.description && scope.row.description.length > 10 ? (scope.row.description.substring(0,10) + '...') : scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteRow(scope.row)">删除</el-button>
          <el-button type="text" size="mini" @click="addResult(scope.row)">添加答案</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="新增题目"
      :visible.sync="dialogVisible"
      width="50%">
      <exam v-if="dialogVisible"/>
    </el-dialog>
    <el-dialog
      title="新增答案"
      :visible.sync="dialogVisible1"
      width="50%">
      <exam v-if="dialogVisible1"/>
    </el-dialog>
  </div>
</template>
<script>
  import {Component, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import Exam from './Exam'

  @Component({
    components: {
      Exam
    }
  })
  export default class Exams extends Mixins(TableBase) {
    currentHtml = 'exams'

    dialogVisible = false

    dialogVisible1 = false

    sucessResult (data) {
      this.formData = data
      this.formData.results = this.formData.results ? this.formData.results : []
    }

    replaceJump (data) {
      this.dialogVisible = true
    }

    getPageUrl () {
      return this.geturl(this.serverUrl.ask.examPage)
    }

    statusFormatter (row) {
      if (row.right === true) {
        return '正确'
      } else {
        return '错误'
      }
    }
    addResult (data) {
      if (!data.reults) {
        data.reults = []
      }
      this.dialogVisible1 = true
    }
  }
</script>
