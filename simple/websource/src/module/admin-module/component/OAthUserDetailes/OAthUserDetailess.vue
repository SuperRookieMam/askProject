<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="账号">
            <el-input v-model="serchObj['username']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="昵称">
            <el-input v-model="serchObj['nickname']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="微信号">
            <el-input v-model="serchObj['weChatId']" placeholder="请输入"/>
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
              style="width: 100%">
      <el-table-column label="id" prop="id"/>
      <el-table-column label="账号" prop="username"/>
      <el-table-column label="昵称" prop="nickname"/>
      <el-table-column label="微信号" prop="weChatId"/>
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
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class OAthUserDetailess extends Mixins(TableBase) {
    currentHtml = 'oAthUserDetailess'
    serchObj = {}

    getPageUrl () {
      return this.geturl(this.serverUrl.ask.oauthUserDetailsPage)
    }
  }
</script>
