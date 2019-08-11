<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="用户账号">
            <el-input v-model="serchObj['userName']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="角色编号">
            <el-input v-model="serchObj['roleId']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="3" style="float: right;">
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
        label="编号"
        prop="id"/>
      <el-table-column
        label="账号"
        prop="userName"/>
      <el-table-column
        label="角色"
        prop="roleName"/>
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
    <el-dialog
      title="新增类型"
      :visible.sync="dialogVisible"
      width="50%">
      <user-role v-if="dialogVisible"/>
    </el-dialog>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import UserRole from './UserRole'
  @Component({
    components: {
      UserRole
    }
  })
  export default class UserRoles extends Mixins(TableBase) {
    currentHtml = 'userRoles'

    dialogVisible = false

    replaceEdit (data) {
      this.setParames('userRole',
        {id: data.id,
          type: 'form',
          parent: this})
      this.dialogVisible = true
    }

    getPageUrl () {
      return this.geturl(this.serverUrl.shopping.userRolePage)
    }
  }
</script>
