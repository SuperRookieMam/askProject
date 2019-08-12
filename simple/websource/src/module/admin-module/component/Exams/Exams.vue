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
      <el-table-column
        label="id"
        prop="id"/>
      <el-table-column label="题目类型" prop="subjectName"/>
      <el-table-column label="答案类型" prop="choose"/>
      <el-table-column label="分值" prop="score"/>
      <el-table-column label="所属菜品" prop="score">
        <template slot-scope="scope">
          {{ scope.row.food ? scope.row.food.foodName : '' }}
        </template>
      </el-table-column>
      <el-table-column label="题目描述" prop="description">
        <template slot-scope="scope">
          {{ scope.row.description && scope.row.description.length > 10 ? (scope.row.description.substring(0,10) + '...') : scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteRow(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="新增题目"
      :visible.sync="dialogVisible"
      width="50%">
      <exam v-if="dialogVisible"/>
    </el-dialog>
    <!--<el-pagination-->
    <!--@size-change="handleSizeChange"-->
    <!--@current-change="handleCurrentChange"-->
    <!--:current-page="params.pageNum"-->
    <!--:page-sizes="pageSizes"-->
    <!--:page-size="params.pageSize"-->
    <!--layout="total, sizes, prev, pager, next, jumper"-->
    <!--:total="totalCount"/>-->
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

    replaceEdit (data) {
      let params = this.getParames('exams')
      this.setParames('exam', {
        type: 'rform',
        id: data.id,
        tableName: 'Food',
        fileName: 'exams',
        rid: params.rid,
        parent: this,
        rparent: params.parent
      })
      this.dialogVisible = true
    }

    getPageUrl () {
      return this.geturl(this.serverUrl.ask.examPage)
    }
  }
</script>
