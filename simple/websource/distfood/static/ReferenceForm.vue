<template>
  <div>
    <el-form size="mini"
             :inline="true"
             :model="searchObj"
             class="clear-float">
      <el-row>
        <el-col :span="18">
          <el-form-item>
            <el-input size="mini" v-model="searchObj.searchName" placeholder="输入搜索关键字"/>
          </el-form-item>
        </el-col>
        <el-col :span="6" class="flex-right">
          <el-form-item>
            <el-button type="primary"
                       size="mini"
                       @click="add()">
              新增
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <ele-data-tables :data="guideShows">
      <el-table-column v-for="(item,index) in tableMesg.trMsg"
                       :key="index"
                       :label="item.label"
                       :prop="item.prop"/>
      <el-table-column label="操作" :min-width="60">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="edit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </ele-data-tables>
  </div>
</template>
<script>
  import { Component, Mixins, Prop } from 'vue-property-decorator'
  import BaseList from './BaseList'
  import EleDataTables from 'element-datatables'

  @Component({
    components: {
      EleDataTables
    }
  })
  export default class Index extends Mixins(BaseList) {
    searchObj = {}

    @Prop({ default: () => {} })
    tableMesg
    @Prop({ default: () => {} })
    pdata
    @Prop({ default: () => '' })
    refname
    @Prop({ default: () => '' })
    fieldname
    @Prop({ default: () => '' })
    routeName
    @Prop({ default: () => '' })
    jumpName

    get guideShows () {
      let datas = this.pdata[this.fieldname]
      if (this.searchObj.searchName === '' ||
        this.searchObj.searchName === undefined ||
        this.searchObj.searchName === 'undefined') {
        return datas
      }
      let date = []
      let i = 0
      datas.forEach((item) => {
        for (let j = 0; j < this.tableMesg.searchNames.length; j++) {
          let item1 = this.tableMesg.searchNames[j]
          if (item[item1].indexOf(this.searchObj.searchName) > -1) {
            date[i] = item
            i++
            break
          }
        }
      })
      return date
    }

    /*  get serverParams () {
        let param = { ...this.searchObj }
        param[this.tableMesg.pname] = this.tableMesg.pvalue
        return param
      } */

    edit ({ id }) {
      this.$router.push({ name: this.routeName, params: { id } })
    }

    add () {
      this.$router.push({
        name: this.routeName,
        params: { id: 'new',
          pid: this.pdata.id,
          refname: this.refname,
          fieldname: this.fieldname,
          jumpName: this.jumpName
        } })
    }
  }
</script>
