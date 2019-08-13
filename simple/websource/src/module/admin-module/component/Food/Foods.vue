<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="菜品名">
            <el-input v-model="serchObj['foodName']" placeholder="请输入"/>
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
    <el-table :data="tableData"
              v-loading="loading"
              style="width: 100%">
      <el-table-column
        label="id"
        prop="id"/>
      <el-table-column label="菜品名" prop="foodName"/>
      <el-table-column label="描述" prop="description">
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
  export default class Foods extends Mixins(TableBase) {
    currentHtml = 'foods'

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
      return this.geturl(this.serverUrl.ask.foodPage)
    }
    statusFormatter (row) {
      if (row.right === true) {
        return '正确'
      } else {
        return '错误'
      }
    }
  }
</script>
