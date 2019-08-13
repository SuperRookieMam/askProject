<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="3" style="float: right">
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
            :data="scope.row.results"
            v-loading="loading"
            :show-header="false"
            :row-class-name="tableRowClassName"
            style="width: 100%;">
            <el-table-column label="id" prop="id"/>
            <el-table-column label="分值" prop="score" :formatter="statusFormatter1"/>
            <el-table-column label="正确" prop="right" :formatter="statusFormatter"/>
            <el-table-column label="答案" prop="description" :formatter="statusFormatter2"/>
            <el-table-column label="操作" :min-width="60">
              <template slot-scope="scope1">
                <el-button type="text" size="mini" @click="rusultEdit(scope1.row,scope.row)">详情</el-button>
                <el-button type="text" size="mini" @click="deleteRusult(scope.row,scope1.row)">删除</el-button>
              </template>
            </el-table-column>
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
          <el-button type="text" size="mini" @click="addRerult(scope.row)">新增答案</el-button>
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
      title="新增题目"
      :visible.sync="dialogVisible1"
      width="50%">
      <result v-if="dialogVisible1"/>
    </el-dialog>
  </div>
</template>
<script>
  import {Component, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import Exam from './Exam'
  import Result from './Result'
  @Component({
    components: {
      Exam,
      Result
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

    statusFormatter1 (row) {
      return row.score + '分'
    }

    statusFormatter2 (row) {
      return row.description && row.description.length < 10 ? row.description : row.description.substring(0, 10)
    }

    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 === 0) {
        return 'warning-row'
      } else {
        return 'success-row'
      }
    }
    addRerult (data) {
      this.rusultEdit({id: 'new'}, data)
    }

    rusultEdit (data, pdata) {
      this.setParames('result', {
        type: 'other',
        id: data.id,
        tableName: 'Exam',
        fileName: 'results',
        rid: pdata.id ? pdata.id : 'new',
        parent: pdata,
        data: data,
        rparent: this
      })
      this.dialogVisible1 = true
    }

    deleteRusult (pdata, data) {
      this.$confirm('确认删除？', '友情提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (let i = 0; i < pdata.results.length; i++) {
          if (JSON.stringify(pdata.results[i]) === JSON.stringify(data)) {
            pdata.results.splice(i, 1)
            return
          }
        }
      })
    }
  }
</script>
<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
