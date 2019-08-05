<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="createTime">
            <el-input v-model="serchObj['createTime']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="modifyTime">
            <el-input v-model="serchObj['modifyTime']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="createUser">
            <el-input v-model="serchObj['createUser']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="modifyUser">
            <el-input v-model="serchObj['modifyUser']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="id">
            <el-input v-model="serchObj['id']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="username">
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
        <el-col :span="6">
          <el-form-item label="password">
            <el-input v-model="serchObj['password']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="headImage">
            <el-input v-model="serchObj['headImage']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="authorities">
            <el-input v-model="serchObj['authorities']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="expired">
            <el-input v-model="serchObj['expired']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="lock">
            <el-input v-model="serchObj['lock']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="credentials">
            <el-input v-model="serchObj['credentials']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="enabled">
            <el-input v-model="serchObj['enabled']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="4">
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
        label="createTime"
        prop="createTime"/>
      <el-table-column
        label="modifyTime"
        prop="modifyTime"/>
      <el-table-column
        label="createUser"
        prop="createUser"/>
      <el-table-column
        label="modifyUser"
        prop="modifyUser"/>
      <el-table-column
        label="id"
        prop="id"/>
      <el-table-column
        label="username"
        prop="username"/>
      <el-table-column
        label="昵称"
        prop="nickname"/>
      <el-table-column
        label="微信号"
        prop="weChatId"/>
      <el-table-column
        label="password"
        prop="password"/>
      <el-table-column
        label="headImage"
        prop="headImage"/>
      <el-table-column
        label="authorities"
        prop="authorities"/>
      <el-table-column
        label="expired"
        prop="expired"/>
      <el-table-column
        label="lock"
        prop="lock"/>
      <el-table-column
        label="credentials"
        prop="credentials"/>
      <el-table-column
        label="enabled"
        prop="enabled"/>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(routeName,scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="deleteRow(controllerMapping,scope.row)">编辑</el-button>
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
  import { Component, Mixins, Prop } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'

  @Component
  export default class OAthUserDetailess extends Mixins(TableBase) {
    @Prop({default: () => 'new'})
    id
    @Prop({default: () => ''})
    rmsg
    @Prop({default: () => ''})
    currentHtml
    serchObj = {}
    params = {
        pageSize: 50,
        pageNum: 1
    }
    pageSizes = [10, 20, 50, 100]

    tableData = []

    routeName = 'oAthUserDetailes'

    controllerMapping = 'data/oAthUserDetailes'

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
