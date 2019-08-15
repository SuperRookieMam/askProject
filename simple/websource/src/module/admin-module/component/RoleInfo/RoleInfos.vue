<template>
  <div>
    <el-form ref="serchObj"
             class="demo-form-inline"
             size="mini"
             label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="编号">
            <el-input v-model="serchObj['id']" placeholder="请输入"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="角色名称">
            <el-input v-model="serchObj['roleName']" placeholder="请输入"/>
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
    <el-table
      :data="tableData"
      v-loading="loading"
      style="width: 100%">
      <el-table-column label="编号" prop="id"/>
      <el-table-column label="角色名称" prop="roleName"/>
      <el-table-column label="父ID" prop="pid"/>
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
      <role-info v-if="dialogVisible"/>
    </el-dialog>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../../plugins/TableBase'
  import RoleInfo from './RoleInfo'
  @Component({
    components: {
      RoleInfo
    }
  })
  export default class RoleInfos extends Mixins(TableBase) {
    currentHtml = 'roleInfos'

    dialogVisible = false

    replaceEdit (data) {
      this.setParames('roleInfo',
        {id: data.id,
          type: 'form',
          parent: this})
      this.dialogVisible = true
    }

    getPageUrl () {
      return this.geturl(this.serverUrl.ask.roleInfoPage)
    }
  }
</script>
