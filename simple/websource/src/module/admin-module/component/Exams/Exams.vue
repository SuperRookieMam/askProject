<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="题目类型">
            <el-input v-model="serchObj['subjectName']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="results">
            <el-input v-model="serchObj['results']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
                     size="mini"
                     @click="filterByserchObj">
            筛选
          </el-button>
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
      <el-table-column
        label="id"
        prop="id"/>
      <el-table-column
        label="题目类型"
        prop="subjectName"/>
      <el-table-column
        label="题目描述"
        prop="description"/>
      <el-table-column
        label="答案类型"
        prop="choose"/>
      <el-table-column
        label="分值"
        :formatter="scoreCover"/>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteRow(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="params.pageNum"
      :page-sizes="pageSizes"
      :page-size="params.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount"/>
  </div>
</template>
<script>
  import {Component, Mixins, Prop} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class Exams extends Mixins(TableBase) {
    @Prop({default: () => 'table'})
    currentHtml
    @Prop({default: () => ''})
    jumpName
    @Prop({default: () => ''})
    rmsg

    howSearch (basicsParams) {
      for (var key in this.serchObj) {
        if (this.serchObj[key] !== '') {
          basicsParams.push({key: key, type: 'eq', value: this.serchObj[key]})
        }
      }
    }
    scoreCover (row) {
      var totalScore = 0
      for (var i in row.results) {
        if (row.results[i].right === true) {
          totalScore = totalScore + row.results[i].score
        }
      }
      return totalScore
    }
    getPageUrl () {
      return this.geturl(this.serverUrl.ask.examPage)
    }
  }
</script>
