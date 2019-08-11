<template>
  <div>
    <el-form :model="newsubnode"
             :rules="rules"
             ref="formData"
             label-width="100px"
             size="mini">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基础信息" name="base">
          <el-row>
            <el-col :span="12">
              <el-row>
                <el-col :span="24">
                  <div class="buttons">
                    <el-button @click="addRoot">新增根节点</el-button>
                    <el-dialog title="根节点信息录入" :visible.sync="dialogFormVisible">
                      <el-form :model="newRoot" ref="rootForm" :rules="rules">
                        <el-form-item label="中文名称" prop="cname">
                          <el-col :span="18">
                            <el-input v-model="newRoot.cname"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="英文名称" prop="ename">
                          <el-col :span="18">
                            <el-input v-model="newRoot.ename"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="排序权重" prop="sort">
                          <el-col :span="18">
                            <el-input-number v-model="newRoot.sort" controls-position="right"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="路由开关" prop="isMenu">
                          <el-col :span="18">
                            <el-switch
                              v-model="newRoot.isMenu"
                              active-color="#13ce66"
                              inactive-color="#ff4949"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="路由" prop="tableRoute" v-if="newRoot.isMenu === true">
                          <el-col :span="18">
                            <el-input v-model="newRoot.tableRoute"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="表单路由" prop="formRoute" v-if="newRoot.isMenu === true">
                          <el-col :span="18">
                            <el-input v-model="newRoot.formRoute"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="类型" prop="type" v-if="newRoot.isMenu === true">
                          <el-col :span="18">
                            <el-input v-model="newRoot.type"/>
                          </el-col>
                        </el-form-item>
                        <el-form-item label="是否显示" prop="isShow">
                          <el-col :span="18">
                            <el-switch
                              v-model="newRoot.isShow"
                              active-color="#13ce66"
                              inactive-color="#ff4949"/>
                          </el-col>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="cancelAddRoot">取 消</el-button>
                        <el-button type="primary" @click="sureAddRoot('rootForm')">确 定</el-button>
                      </div>
                    </el-dialog>
                  </div>
                </el-col>
                <el-col :span="24">
                  <div class="tree-div">
                    <el-tree :data="data"
                             :props="defaultProps"
                             :highlight-current="true"
                             :default-expand-all="true"
                             @node-click="handleNodeClick"/>
                  </div>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-col :span="12">
                  <el-form-item>
                    <h3>当前选中节点信息</h3>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="id" prop="id">
                    <el-input v-model="currentParentNode.id" :disabled="true"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="父id">
                    <el-input type="number" v-model="currentParentNode.pid"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="中文名称">
                    <el-input v-model="currentParentNode.cname"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item label="路由开关" prop="isMenu">
                <el-col :span="12">
                  <el-switch
                    v-model="currentParentNode.isMenu"
                    active-color="#13ce66"
                    inactive-color="#ff4949"/>
                </el-col>
              </el-form-item>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="路由">
                    <el-input v-model="currentParentNode.tableRoute" v-if="currentParentNode.isMenu"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="表单路由">
                    <el-input v-model="currentParentNode.formRoute" v-if="currentParentNode.isMenu"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="路由类型">
                    <el-input v-model="currentParentNode.type" v-if="currentParentNode.isMenu"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item>
                    <el-button type="primary" @click="updateNode">
                      修改
                    </el-button>
                    <el-button type="primary" @click="deleteNode">
                      删除
                    </el-button>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item>
                    <h3>子节点信息录入</h3>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="中文名称" prop="cname">
                    <el-input v-model="newsubnode.cname"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="英文名称" prop="ename">
                    <el-input v-model="newsubnode.ename"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="路由开关" prop="isMenu">
                    <el-switch
                      v-model="newsubnode.isMenu"
                      active-color="#13ce66"
                      inactive-color="#ff4949"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="路由名称" prop="tableRoute" v-if="newsubnode.isMenu">
                    <el-input v-model="newsubnode.tableRoute"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="表单路由" prop="formRoute" v-if="newsubnode.isMenu">
                    <el-input v-model="newsubnode.formRoute"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="路由类型" prop="type" v-if="newsubnode.isMenu">
                    <el-input v-model="newsubnode.type"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="排序权重" prop="sort">
                    <el-input type="number" v-model="newsubnode.sort"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="是否显示" prop="isShow">
                    <el-switch
                      v-model="newsubnode.isShow"
                      active-color="#13ce66"
                      inactive-color="#ff4949"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item>
                    <el-button type="primary" @click="sureAddSub('formData')">
                      保存
                    </el-button>
                    <el-button @click="cancelAddSub">
                      取消
                    </el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-form>
  </div>
</template>
<script>
  import { Component, Mixins } from 'vue-property-decorator'
  import TableBase from '../../../plugins/TableBase'

  @Component
  export default class MenuTree extends Mixins(TableBase) {
    activeName = 'base'
    dialogFormVisible = false
    newRoot = {}
    currentParentNode = {}
    newsubnode = {}
    data = []
    rules = {
      cname: [{required: true, message: '请输入中文名称', trigger: 'blur'}],
      ename: [{required: true, message: '请输入英文名称', trigger: 'change'}],
      sort: [{required: true, message: '请输入排序权重', trigger: 'change'}]
    }
    defaultProps = {
      children: 'children',
      label: 'cname'
    }

    addRoot () {
      this.newRoot = {}
      this.dialogFormVisible = true
    }

    cancelAddRoot () {
      this.newRoot = {}
      this.dialogFormVisible = false
    }

    sureAddRoot (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let tempRoot = {}
          tempRoot.cname = this.newRoot.cname
          tempRoot.ename = this.newRoot.ename
          tempRoot.sort = this.newRoot.sort
          tempRoot.formRoute = this.newRoot.formRoute ? this.newRoot.formRoute : ''
          tempRoot.isMenu = this.newRoot.isMenu ? 1 : 0
          tempRoot.isShow = this.newRoot.isShow ? 1 : 0
          tempRoot.tableRoute = this.newRoot.tableRoute
          tempRoot.type = this.newRoot.type
          tempRoot.pid = 0
          this.insert({url: this.geturl(this.serverUrl.ask.menutreeUpdate), params: [tempRoot]}).then(data => {
            if (data.code === 0) {
              let arr = data.data
              this.data.push(arr[0])
              this.dialogFormVisible = false
              this.newsubnode = {}
              this.refreshTree()
            } else {
              this.message('新增根节点失败', '提示信息')
            }
          })
        } else {
          return false
        }
      })
    }

    cancelAddSub () {
      this.newsubnode = {}
    }

    sureAddSub (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let tempsub = {}
          tempsub.cname = this.newsubnode.cname
          tempsub.ename = this.newsubnode.ename
          tempsub.sort = this.newsubnode.sort
          tempsub.formRoute = this.newsubnode.formRoute
          tempsub.isMenu = this.newsubnode.isMenu ? 1 : 0
          tempsub.isShow = this.newsubnode.isShow ? 1 : 0
          tempsub.tableRoute = this.newsubnode.tableRoute
          tempsub.type = this.newsubnode.type
          tempsub.pid = this.currentParentNode.id
          this.insert({url: this.geturl(this.serverUrl.ask.menutreeUpdate), params: [tempsub]}).then(data => {
            if (data.code === 0) {
              let arr = data.data
              if (this.currentParentNode.children) {
                this.currentParentNode.children.push(arr[0])
              } else {
                this.currentParentNode.children = arr
              }
              this.refreshTree()
            } else {
              this.message('新增子节点失败', '提示信息')
            }
          })
        } else {
          return false
        }
      })
    }

    refreshTree () {
      this.select(this.geturl(this.serverUrl.ask.menutreeList), {}, true).then(data => {
        this.data = this.getTree(data, 'children', 'pid')
      })
    }

    handleNodeClick (data) {
      this.currentParentNode = data
    }

    getchieldId (arr, obj) {
      if (arr) {
        arr.forEach(ele => {
          obj[ele.id] = 1
          if (ele.hasOwnProperty('children')) {
            this.getchieldId(ele.children, obj)
          }
        })
      }
    }

    updateNode () {
      if (!this.currentParentNode.id ||
         (!this.currentParentNode.pid && this.currentParentNode.pid !== 0) ||
          !this.currentParentNode.cname) {
        this.message('请完整选中状态的节点信息', '友情提示')
        return
      }
      // 防止形成循环依赖
      if (this.currentParentNode.hasOwnProperty('children')) {
        let keyObj = {}
        this.getchieldId(this.currentParentNode.children, keyObj)
        if (keyObj.hasOwnProperty(this.currentParentNode.pid)) {
          this.message('请勿设置当前节点的子节点为父节点', '友情提示')
          return
        }
        delete this.currentParentNode.children
      }
      this.currentParentNode.isMenu = this.currentParentNode.isMenu === true || this.currentParentNode.isMenu === 1 ? 1 : 0
      this.update({url: this.geturl(this.serverUrl.ask.menutreeUpdate), params: [this.currentParentNode]}).then(data => {
        if (data.code === 0) {
          this.currentParentNode = {}
          this.refreshTree()
        } else {
          this.message('修改节点失败', '友情信息')
        }
      })
    }

    deleteNode () {
      if (!this.currentParentNode.id ||
         (!this.currentParentNode.pid && this.currentParentNode.pid !== 0) ||
          !this.currentParentNode.cname) {
        this.message('请完整选中状态的节点信息', '友情提示')
        return
      }
      if (this.currentParentNode.children && this.currentParentNode.children.length > 0) {
        this.message('请先保存子节点的信息', '友情提示')
        return
      }
      this.$confirm('确认删除数据', '友情提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.remove(this.geturl(`${this.serverUrl.ask.menutreeUpdate}/${this.currentParentNode.id}`)).then(data => {
          this.refreshTree()
          this.$message({
            type: 'info',
            message: '删除成功'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }

    handleClick (tab, event) {
      /* if (tab.name === 'formtest') {
          this.$router.push({name: 'tt', params: {rowData: {id: 'new'}}})
        } */
    }

    created () {
      this.refreshTree()
    }
  }
</script>
<style>
  .tree-div {
    width: 100%;
    height: 500px;
    overflow: scroll;
  }
</style>
