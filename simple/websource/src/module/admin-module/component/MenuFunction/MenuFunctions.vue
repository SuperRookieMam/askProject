<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="中文名称">
            <el-input v-model="serchObj['cname']"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="英文名">
            <el-input v-model="serchObj['ename']"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="路由名称">
            <el-input v-model="serchObj['routeName']"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-button type="primary"
                     size="mini"
                     @click="filterByserchObj()">
            筛选
          </el-button>
          <el-button type="primary"
                     size="mini"
                     @click="add(routeName)">
            新增
          </el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-table :data="tableData"
              style="width: 100%">
      <el-table-column
        label="id"
        prop="id"/>
      <el-table-column
        label="中文名称"
        prop="cname"/>
      <el-table-column
        label="英文名"
        prop="ename"/>
      <el-table-column
        label="跳转地址"
        prop="url"/>
      <el-table-column
        label="排序"
        prop="sort"/>
      <el-table-column
        label="路由名称"
        prop="routeName"/>
      <el-table-column
        label="父ID"
        prop="pid"/>
      <el-table-column
        label="中文名称"
        prop="isMenu"/>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(routeName,scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteRow(controllerMapping,scope.row)">删除</el-button>
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
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  @Component
  export default class MenuFunctions extends Mixins(TableBase) {
    serchObj = {}
    params = {
        pageSize: 50,
        pageNum: 1
    }
    pageSizes = [10, 20, 50, 100]

    tableData = []

    routeName = 'menuFunction'

    controllerMapping = 'data/menuFunction'

    handleSizeChange (val) {
     this.params.pageSize = val
      this.filterByserchObj()
    }
    handleCurrentChange (val) {
      this.params.pageNum = val
      this.filterByserchObj()
    }

    filterByserchObj () {
      let basicsParams = []
      for (var key in this.serchObj) {
        if (this.serchObj[key] !== '') {
          basicsParams.push({key: key, type: 'eq', value: this.serchObj[key]})
        }
      }
      this.params.basicsParams = basicsParams
      this.select(`${this.controllerMapping}/page`, this.params, true).then(data => {
          this.tableData = data.list
          this.params.pageSize = data.pageSize
          this.params.pageNum = data.pageNum
          this.totalCount = data.total
      })
    }
    deleteRow (url, params) {
      this.Del({url: `${url}/${params.id}`}).then(data => {
        this.filterByserchObj()
      })
    }
    created () {
      this.filterByserchObj()
    }
  }
</script>
