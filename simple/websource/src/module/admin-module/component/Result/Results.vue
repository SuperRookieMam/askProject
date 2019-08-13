<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="所属题目">
            <el-input v-model="serchObj['exam.subjectName']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否正确">
            <el-input v-model="serchObj['right']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="3" style="float: right">
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
    <el-table
      :data="tableData"
      v-loading="loading"
      style="width: 100%">
      <el-table-column label="id" prop="id"/>
      <el-table-column label="答案" prop="description"/>
      <el-table-column label="分值" prop="score"/>
      <el-table-column label="是否正确" prop="right" :formatter="statusFormatter"/>
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
  import {Component, Mixins} from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class Results extends Mixins(TableBase) {
    currentHtml = 'results'

    statusFormatter (row) {
     if (row.right === true) {
       return '正确'
     } else {
       return '错误'
     }
    }
    setRequestParam (params) {
      let basicsParams = []
      for (var key in this.serchObj) {
        if (this.serchObj[key] !== '') {
          basicsParams.push({key: key, type: 'like', value: this.serchObj[key]})
        }
      }
      this.params.basicsParams = basicsParams
    }
    getPageUrl () {
      return this.geturl(this.serverUrl.ask.resultPage)
    }
  }
</script>
